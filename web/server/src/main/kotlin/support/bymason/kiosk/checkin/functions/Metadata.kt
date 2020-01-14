package support.bymason.kiosk.checkin.functions

import firebase.functions.AuthContext
import firebase.functions.admin
import firebase.https.CallableContext
import firebase.https.HttpsError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlin.js.Json
import kotlin.js.Promise

fun getCompanyMetadata(context: CallableContext): Promise<Json> {
    val auth = context.auth ?: throw HttpsError("unauthenticated")
    console.log("Generating company metadata for user '${auth.uid}'")

    return GlobalScope.async { getCompanyMetadata(auth) }.asPromise()
}

private suspend fun getCompanyMetadata(auth: AuthContext): Json {
    val fieldsSnap = admin.firestore()
            .collection(auth.uid)
            .doc("config")
            .collection("metadata")
            .doc("company")
            .get()
            .await()

    val metadata = fieldsSnap.data()
    console.log("Retrieved company metadata:", metadata)
    return metadata
}
