package support.bymason.kiosk.checkin.functions

import firebase.functions.AuthContext
import firebase.functions.admin
import kotlinx.coroutines.await
import kotlin.js.Json

suspend fun getGuestFields(auth: AuthContext): Array<Json> {
    console.log("Generating guest fields for user '${auth.uid}'")

    val fieldsSnap = admin.firestore()
            .collection(auth.uid)
            .doc("config")
            .collection("guest-fields")
            .get()
            .await()

    val fields = fieldsSnap.docs.map {
        it.data().apply { set("id", it.id) }
    }.sortedBy {
        it["position"] as Int
    }.toTypedArray()

    console.log("Retrieved guest fields:", fields)
    return fields
}
