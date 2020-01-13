package com.bymason.kiosk.checkin.functions

import com.bymason.kiosk.checkin.utils.createInstance
import com.bymason.kiosk.checkin.utils.fetchPopulatedSession
import com.bymason.kiosk.checkin.utils.getAndInitCreds
import com.bymason.kiosk.checkin.utils.refreshDocusignCreds
import firebase.firestore.DocumentSnapshot
import firebase.firestore.FieldValues
import firebase.firestore.SetOptions
import firebase.functions.AuthContext
import firebase.functions.admin
import firebase.https.CallableContext
import firebase.https.HttpsError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.Json
import kotlin.js.Promise
import kotlin.js.json

fun generateNdaLink(data: Any?, context: CallableContext): Promise<Json>? {
    val auth = context.auth ?: throw HttpsError("unauthenticated")
    val sessionId = data as? String
    console.log("Generating DocuSign NDA for user '${auth.uid}' with session '$sessionId'")

    if (sessionId == null) {
        throw HttpsError("invalid-argument")
    }

    return GlobalScope.async { generateNdaLink(auth, sessionId) }.asPromise()
}

private suspend fun generateNdaLink(auth: AuthContext, sessionId: String): Json {
    val (sessionDoc, session) = fetchPopulatedSession(auth.uid, sessionId)
    val guestFields = session["guestFields"] as Array<Json>
    val guestName = guestFields.mapNotNull { field ->
        if (field["type"] == 0) field["value"] as String else null
    }.single()
    val guestEmail = guestFields.mapNotNull { field ->
        if (field["type"] == 1) field["value"] as String else null
    }.single()
    val guestCompany = guestFields.mapNotNull { field ->
        if (field["type"] == 2) field["value"] as String else null
    }.singleOrNull()

    val creds = getAndInitCreds(auth.uid, "docusign")
    val docusignCreds = creds.getValue("docusign")
    val metadataRef = admin.firestore()
            .collection(auth.uid)
            .doc("config")
            .collection("metadata")
    val companyName = metadataRef.doc("company").get().await().data()["name"]
    val ndaType = if (guestCompany == null) "individual-nda" else "corporate-nda"
    val ndaRef = metadataRef.doc(ndaType).get().await()
    if (!ndaRef.exists) throw HttpsError("not-found", "No NDA found.")
    val ndaBase64 = admin.asDynamic().storage().bucket()
            .file(ndaRef.data()["store"])
            .download()
            .unsafeCast<Promise<dynamic>>()
            .await()[0]
            .toString("base64")

    val docusign = js("require('docusign-esign')")
    val docusignClient: dynamic = createInstance(docusign.ApiClient)
    docusignClient.setBasePath(docusignCreds.asDynamic().accounts[0].base_uri + "/restapi")
    docusignClient.addDefaultHeader(
            "Authorization", "Bearer ${docusignCreds["access_token"]}")
    docusign.Configuration.default.setDefaultApiClient(docusignClient)

    val envelope: dynamic = createInstance(docusign.EnvelopeDefinition)
    envelope.status = "sent"
    envelope.emailSubject = "$companyName Nondisclosure Agreement"

    val document = docusign.Document.constructFromObject(json(
            "documentBase64" to ndaBase64,
            "fileExtension" to "pdf",
            "name" to "$companyName Nondisclosure Agreement",
            "documentId" to "1"
    ))
    envelope.documents = arrayOf(document)

    val signer = docusign.Signer.constructFromObject(json(
            "name" to guestName,
            "email" to guestEmail,
            "routingOrder" to "1",
            "recipientId" to "1",
            "clientUserId" to auth.uid
    ))
    signer.tabs = docusign.Tabs.constructFromObject(json(
            "fullNameTabs" to ndaRef.getTabs(docusign, "names"),
            "dateSignedTabs" to ndaRef.getTabs(docusign, "dates"),
            "signHereTabs" to ndaRef.getTabs(docusign, "signatures")
    ))
    if (guestCompany != null) {
        signer.tabs.textTabs = ndaRef.getTabs(docusign, "companies")
        for (tab in signer.tabs.textTabs) {
            tab.value = guestCompany
        }
    }
    envelope.recipients =
            docusign.Recipients.constructFromObject(json("signers" to arrayOf(signer)))

    val accountId = docusignCreds["accounts"]
            .unsafeCast<Array<Json>>().first()["account_id"]
    val envelopeApi: dynamic = createInstance(docusign.EnvelopesApi)
    val envelopeResult: dynamic = makeDocusignRequest(
            auth,
            docusignCreds,
            docusignClient
    ) { handler ->
        envelopeApi.createEnvelope(
                accountId,
                json("envelopeDefinition" to envelope),
                handler
        )
    }
    val envelopeId = envelopeResult.envelopeId
    val viewResult: dynamic = makeDocusignRequest(
            auth,
            docusignCreds,
            docusignClient
    ) { handler ->
        val viewRequest = docusign.RecipientViewRequest.constructFromObject(json(
                "authenticationMethod" to "None",
                "clientUserId" to auth.uid,
                "recipientId" to "1",
                "returnUrl" to "https://mason-check-in-kiosk.firebaseapp.com/redirect/docusign/app",
                "userName" to guestName,
                "email" to guestEmail
        ))
        envelopeApi.createRecipientView(
                accountId,
                envelopeResult.envelopeId,
                json("recipientViewRequest" to viewRequest),
                handler
        )
    }

    console.log("Generated NDA for $guestName ($guestEmail) to sign: ", viewResult.url)

    sessionDoc.ref.set(json(
            "state" to 2,
            "timestamp" to FieldValues.serverTimestamp(),
            "documents" to json(
                    "nda" to "https://appdemo.docusign.com/documents/details/$envelopeId"
            )
    ), SetOptions.merge).await()

    return json("url" to viewResult.url)
}

private fun DocumentSnapshot.getTabs(
        docusign: dynamic,
        type: String
): Array<dynamic> = data()[type].unsafeCast<Array<Json>>().map { tab ->
    docusign.SignHere.constructFromObject(json(
            "documentId" to "1",
            "recipientId" to "1",
            "pageNumber" to tab["page"],
            "xPosition" to tab["x"],
            "yPosition" to tab["y"]
    ))
}.toTypedArray()

private suspend fun <T> makeDocusignRequest(
        auth: AuthContext,
        creds: Json,
        docusignClient: dynamic,
        block: (handler: dynamic) -> Unit
): T {
    val scope = CoroutineScope(coroutineContext)
    return suspendCoroutine { cont ->
        var hasRetried = false
        lateinit var handler: Any
        handler = fun(error: dynamic, result: dynamic, response: dynamic) {
            if (!hasRetried) {
                hasRetried = true
                if (response.statusCode == 401) {
                    scope.launch {
                        val newCreds = refreshDocusignCreds(auth.uid, creds)
                        docusignClient.addDefaultHeader(
                                "Authorization", "Bearer ${newCreds["access_token"]}")
                        block(handler)
                    }.invokeOnCompletion {
                        it?.let { cont.resumeWithException(it) }
                    }
                    return
                }
            }

            if (error == null) {
                cont.resume(result)
            } else {
                cont.resumeWithException(error)
            }
        }

        block(handler)
    }
}
