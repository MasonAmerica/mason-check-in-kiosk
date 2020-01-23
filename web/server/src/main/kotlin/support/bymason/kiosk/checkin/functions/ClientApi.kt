package support.bymason.kiosk.checkin.functions

import firebase.https.CallableContext
import firebase.https.HttpsError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlin.js.Json
import kotlin.js.Promise

fun processClientRequest(data: Json, context: CallableContext): Promise<Any?> {
    val auth = context.auth ?: throw HttpsError("unauthenticated")
    val rawOperation = data["operation"] as? String
    console.log(
            "Processing '$rawOperation' check-in operation for user '${auth.uid}' with args: ",
            JSON.stringify(data)
    )

    if (rawOperation == null) {
        throw HttpsError("invalid-argument")
    }
    val operation = rawOperation.toUpperCase().replace("-", "_")

    return GlobalScope.async {
        when (operation) {
            "GET_COMPANY_METADATA" -> getCompanyMetadata(auth)
            "GET_GUEST_FIELDS" -> getGuestFields(auth)
            "SESSION_CREATE" -> createSession(auth, data)
            "SESSION_FIND_HOSTS" -> findHosts(auth, data)
            "SESSION_HERE_TO_SEE" -> hereToSee(auth, data)
            "SESSION_NDA_LINK" -> generateNdaLink(auth, data)
            "SESSION_FINALIZE" -> finalizeSession(auth, data)
            else -> throw HttpsError("invalid-argument")
        }
    }.asPromise()
}
