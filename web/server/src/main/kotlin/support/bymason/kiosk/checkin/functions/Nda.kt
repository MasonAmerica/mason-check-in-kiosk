package support.bymason.kiosk.checkin.functions

import firebase.firestore.DocumentSnapshot
import firebase.firestore.FieldValues
import firebase.firestore.SetOptions
import firebase.functions.AuthContext
import firebase.functions.admin
import firebase.https.HttpsError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import superagent.superagent
import support.bymason.kiosk.checkin.utils.createInstance
import support.bymason.kiosk.checkin.utils.fetchGsuiteHost
import support.bymason.kiosk.checkin.utils.fetchPopulatedSession
import support.bymason.kiosk.checkin.utils.getAndInitCreds
import support.bymason.kiosk.checkin.utils.refreshDocusignCreds
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.Json
import kotlin.js.json

suspend fun generateNdaLink(auth: AuthContext, data: Json): Json {
    val sessionId = data["id"] as? String
            ?: throw HttpsError("invalid-argument")

    return generateNdaLink(auth, sessionId)
}

private suspend fun generateNdaLink(auth: AuthContext, sessionId: String): Json {
    console.log("Generating DocuSign NDA for user '${auth.uid}' with session '$sessionId'")

    val (sessionDoc, session) = fetchPopulatedSession(auth.uid, sessionId)
    val host = fetchGsuiteHost(auth.uid, session.asDynamic().hereToSee[0].gsuite)
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
    val isTestRequest = guestEmail.split("@").lastOrNull() ==
            host.primaryEmail?.split("@")?.lastOrNull()

    val metadataRef = admin.firestore()
            .collection(auth.uid)
            .doc("config")
            .collection("metadata")
    val companyName = metadataRef.doc("company").get().await().data()["name"]
    val ndaType = if (guestCompany == null) "individual-nda" else "corporate-nda"
    val documentsRef = metadataRef.doc("documents").get().await()
    val ndaProvider = documentsRef.data()["nda"] as String?
            ?: throw HttpsError("not-found", "No NDA found.")

    return when (ndaProvider) {
        "docusign" -> generateDocusignLink(
                auth,
                ndaType,
                companyName,
                documentsRef.data().asDynamic().docusign[ndaType],
                guestCompany,
                guestName,
                guestEmail,
                sessionDoc
        )
        "esignatures" -> generateEsignaturesLink(
                auth,
                ndaType,
                companyName,
                documentsRef.data().asDynamic().esignatures[ndaType],
                guestCompany,
                guestName,
                guestEmail,
                sessionDoc,
                isTestRequest
        )
        else -> throw HttpsError("failed-precondition", "No NDA providers configured.")
    }
}

private suspend fun generateDocusignLink(
        auth: AuthContext,
        ndaType: String,
        companyName: Any?,
        templateId: String,
        guestCompany: String?,
        guestName: String,
        guestEmail: String,
        sessionDoc: DocumentSnapshot
): Json {
    val creds = getAndInitCreds(auth.uid, "docusign")
    val docusignCreds = creds.getValue("docusign")
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

    console.log("Generated NDA for $guestName ($guestEmail) to sign: ", JSON.stringify(viewResult))

    sessionDoc.ref.set(json(
            "state" to 2,
            "timestamp" to FieldValues.serverTimestamp(),
            "documents" to json(
                    "nda" to "https://appdemo.docusign.com/documents/details/$envelopeId"
            )
    ), SetOptions.merge).await()

    return json("url" to viewResult.url)
}

private suspend fun generateEsignaturesLink(
        auth: AuthContext,
        ndaType: String,
        companyName: Any?,
        templateId: String,
        guestCompany: String?,
        guestName: String,
        guestEmail: String,
        sessionDoc: DocumentSnapshot,
        isTestRequest: Boolean
): Json {
    val creds = getAndInitCreds(auth.uid, "esignatures")
    val esignaturesCreds = creds.getValue("esignatures")
    val esignaturesSecret = esignaturesCreds["secret"]

    val userVisibleNdaType = if (ndaType == "individual-nda") "Individual" else "Corporate"
    val signer = json(
            "name" to guestName,
            "email" to guestEmail,
            "redirect_url" to "https://mason-check-in-kiosk.firebaseapp.com/redirect/docusign/app" +
                    "?event=signing_complete",
            "embedded_sign_page" to "yes",
            "skip_signature_request_email" to "yes"
    )
    if (guestCompany != null) {
        signer["company_name"] = guestCompany
    }

    val contract = superagent.post("https://$esignaturesSecret:@esignatures.io/api/contracts")
            .send(json(
                    "template_id" to templateId,
                    "title" to "$companyName $userVisibleNdaType Nondisclosure Agreement",
                    "signers" to arrayOf(signer),
                    "test" to if (isTestRequest) "yes" else "no"
            ))
            .await().body
    val contractData = contract.asDynamic().data.contract
    val url = contractData.signers[0].embedded_url

    console.log("Generated NDA for $guestName ($guestEmail) to sign: ",
                JSON.stringify(contractData))

    sessionDoc.ref.set(json(
            "state" to 2,
            "timestamp" to FieldValues.serverTimestamp(),
            "documents" to json(
                    "nda" to "https://esignatures.io/contracts/${contractData.id}"
            )
    ), SetOptions.merge).await()

    return json("url" to url)
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
