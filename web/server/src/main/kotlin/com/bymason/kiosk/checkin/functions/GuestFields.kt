package com.bymason.kiosk.checkin.functions

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

fun getGuestFields(context: CallableContext): Promise<Array<Json>> {
    val auth = context.auth ?: throw HttpsError("unauthenticated")
    console.log("Generating guest fields for user '${auth.uid}'")

    return GlobalScope.async { getGuestFields(auth) }.asPromise()
}

private suspend fun getGuestFields(auth: AuthContext): Array<Json> {
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
