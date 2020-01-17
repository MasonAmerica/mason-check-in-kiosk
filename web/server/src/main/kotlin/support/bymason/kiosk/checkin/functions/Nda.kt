package support.bymason.kiosk.checkin.functions

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
import support.bymason.kiosk.checkin.utils.createInstance
import support.bymason.kiosk.checkin.utils.fetchPopulatedSession
import support.bymason.kiosk.checkin.utils.getAndInitCreds
import support.bymason.kiosk.checkin.utils.refreshDocusignCreds
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
    val documentsRef = metadataRef.doc("documents").get().await()
    val templateId = documentsRef.data()[ndaType] as String?
            ?: throw HttpsError("not-found", "No NDA found.")

    val docusign = js("require('docusign-esign')")
    val docusignClient: dynamic = createInstance(docusign.ApiClient)
    docusignClient.setBasePath(docusignCreds.asDynamic().accounts[0].base_uri + "/restapi")
    docusignClient.addDefaultHeader(
            "Authorization", "Bearer ${docusignCreds["access_token"]}")
    docusign.Configuration.default.setDefaultApiClient(docusignClient)

    val envelope: dynamic = createInstance(docusign.EnvelopeDefinition)
    envelope.status = "sent"
    val userVisibleNdaType = if (ndaType == "individual-nda") "Individual" else "Corporate"
    envelope.emailSubject = "$companyName $userVisibleNdaType Nondisclosure Agreement"
    envelope.templateId = templateId

    val tabs = docusign.Tabs.constructFromObject(json())
    if (guestCompany != null) {
        tabs.textTabs = arrayOf(json("tabLabel" to "SignerCompany", "value" to guestCompany))
    }

    val role = docusign.TemplateRole.constructFromObject(json(
            "roleName" to "Signer",
            "name" to guestName,
            "email" to guestEmail,
            "tabs" to tabs,
            "clientUserId" to auth.uid
    ))

    envelope.templateRoles = arrayOf(role)

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
