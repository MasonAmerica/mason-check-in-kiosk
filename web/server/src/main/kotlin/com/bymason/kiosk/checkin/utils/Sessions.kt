package com.bymason.kiosk.checkin.utils

import firebase.functions.admin
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.js.Json

suspend fun fetchPopulatedSession(uid: String, sessionId: String): Json = coroutineScope {
    val sessionDoc = admin.firestore().collection(uid)
            .doc("sessions")
            .collection("visits")
            .doc(sessionId)
            .get()
            .await()
    val session = sessionDoc.data()

    (session["guestFields"] as Array<Json>).map { completedField ->
        async {
            val field = admin.firestore().collection(uid)
                    .doc("config")
                    .collection("guest-fields")
                    .doc(completedField["id"] as String)
                    .get()
                    .await()
                    .data()

            completedField["type"] = field["type"]
        }
    }.awaitAll()

    session
}
