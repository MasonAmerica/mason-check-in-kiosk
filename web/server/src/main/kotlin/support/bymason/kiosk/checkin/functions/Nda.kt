package support.bymason.kiosk.checkin.functions

import firebase.firestore.DocumentSnapshot
import firebase.firestore.FieldValues
import firebase.firestore.SetOptions
import firebase.functions.AuthContext
import firebase.functions.admin
import firebase.https.HttpsError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import superagent.superagent
import support.bymason.kiosk.checkin.utils.createInstance
import support.bymason.kiosk.checkin.utils.fetchGsuiteHost
import support.bymason.kiosk.checkin.utils.fetchValidatedSession
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
    console.log("Generating NDA for user '${auth.uid}' with session '$sessionId'")

    val sessionDoc = fetchValidatedSession(auth.uid, sessionId)
    val signingData = fetchSigningDataAsync(auth, sessionDoc)

    return when (signingData.ndaProvider) {
        "docusign" -> generateDocusignLink(auth, signingData, sessionDoc)
        "esignatures" -> generateEsignaturesLink(auth, signingData, sessionDoc)
        else -> throw HttpsError(
                "failed-precondition", "Unknown NDA provider : ${signingData.ndaProvider}")
    }
}

private suspend fun generateDocusignLink(
        auth: AuthContext,
        signingData: SigningData,
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
    val userVisibleNdaType = if (signingData.ndaType == "individual-nda") {
        "Individual"
    } else {
        "Corporate"
    }
    envelope.emailSubject = "${signingData.companyName} $userVisibleNdaType Nondisclosure Agreement"
    envelope.templateId = signingData.templateId

    val tabs = docusign.Tabs.constructFromObject(json())
    if (signingData.guestCompany != null) {
        tabs.textTabs = arrayOf(json(
                "tabLabel" to "SignerCompany",
                "value" to signingData.guestCompany
        ))
    }

    val role = docusign.TemplateRole.constructFromObject(json(
            "roleName" to "Signer",
            "name" to signingData.guestName,
            "email" to signingData.guestEmail,
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
                "userName" to signingData.guestName,
                "email" to signingData.guestEmail
        ))
        envelopeApi.createRecipientView(
                accountId,
                envelopeResult.envelopeId,
                json("recipientViewRequest" to viewRequest),
                handler
        )
    }

    console.log(
            "Generated DocuSign NDA for ${signingData.guestName} " +
                    "(${signingData.guestEmail}) to sign: ",
            JSON.stringify(viewResult)
    )

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
        signingData: SigningData,
        sessionDoc: DocumentSnapshot
): Json {
    val creds = getAndInitCreds(auth.uid, "esignatures")
    val esignaturesCreds = creds.getValue("esignatures")
    val esignaturesSecret = esignaturesCreds["secret"]

    val userVisibleNdaType = if (signingData.ndaType == "individual-nda") {
        "Individual"
    } else {
        "Corporate"
    }
    val signer = json(
            "name" to signingData.guestName,
            "email" to signingData.guestEmail,
            "redirect_url" to "https://mason-check-in-kiosk.firebaseapp.com/redirect/docusign/app" +
                    "?event=signing_complete",
            "embedded_sign_page" to "yes",
            "skip_signature_request_email" to "yes"
    )
    if (signingData.guestCompany != null) {
        signer["company_name"] = signingData.guestCompany
    }

    val contract = superagent.post("https://$esignaturesSecret:@esignatures.io/api/contracts")
            .send(json(
                    "template_id" to signingData.templateId,
                    "title" to "${signingData.companyName} $userVisibleNdaType " +
                            "Nondisclosure Agreement",
                    "signers" to arrayOf(signer),
                    "test" to if (signingData.isTestRequest) "yes" else "no"
            ))
            .await().body
    val contractData = contract.asDynamic().data.contract
    val url = contractData.signers[0].embedded_url

    console.log(
            "Generated eSignatures NDA for ${signingData.guestName} " +
                    "(${signingData.guestEmail}) to sign: ",
            JSON.stringify(contractData)
    )

    sessionDoc.ref.set(json(
            "state" to 2,
            "timestamp" to FieldValues.serverTimestamp(),
            "documents" to json(
                    "nda" to "https://esignatures.io/contracts/${contractData.id}"
            )
    ), SetOptions.merge).await()

    return json("url" to url)
}

private suspend fun fetchSigningDataAsync(
        auth: AuthContext,
        sessionDoc: DocumentSnapshot
) = coroutineScope {
    val session = sessionDoc.data()

    val guestFields = session["guestFields"] as Array<Json>
    val guestName = guestFields.mapNotNull { field ->
        if (field["type"] == 0) field["value"] as String else null
    }.single()
    val guestEmail = guestFields.mapNotNull { field ->
        if (field["type"] == 1) field["value"] as String else null
    }.single()
    val guestCompany = guestFields.mapNotNull { field ->
        if (field["type"] == 2) field["value"] as String? else null
    }.singleOrNull()

    val metadataRef = admin.firestore()
            .collection(auth.uid)
            .doc("config")
            .collection("metadata")

    val companyName = async {
        metadataRef.doc("company").get().await().data()["name"] as String?
    }
    val documentsData = async {
        metadataRef.doc("documents").get().await().data()
    }
    val isTestRequest = async {
        val host = fetchGsuiteHost(auth.uid, session.asDynamic().hereToSee[0].gsuite)
        guestEmail.split("@").lastOrNull() ==
                host.primaryEmail?.split("@")?.lastOrNull()
    }

    val ndaType = if (guestCompany == null) "individual-nda" else "corporate-nda"
    val ndaProvider = documentsData.await()["nda"] as String?
            ?: throw HttpsError("not-found", "No NDA found.")
    val templates = documentsData.await()[ndaProvider] as? Json
            ?: throw HttpsError("not-found", "No NDA found for provider: $ndaProvider")
    val templateId = templates[ndaType] as? String
            ?: throw HttpsError("not-found", "No NDA found for type: $ndaType")

    SigningData(
            templateId,
            ndaProvider,
            ndaType,
            companyName.await(),
            guestName,
            guestEmail,
            guestCompany,
            isTestRequest.await()
    )
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

data class SigningData(
        val templateId: String,
        val ndaProvider: String,
        val ndaType: String,
        val companyName: String?,
        val guestName: String,
        val guestEmail: String,
        val guestCompany: String?,
        val isTestRequest: Boolean
)
