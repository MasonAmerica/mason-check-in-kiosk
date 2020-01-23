package support.bymason.kiosk.checkin.functions

import firebase.functions.AuthContext
import firebase.functions.admin
import kotlinx.coroutines.await
import kotlin.js.Json

suspend fun getCompanyMetadata(auth: AuthContext): Json {
    console.log("Generating company metadata for user '${auth.uid}'")

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
