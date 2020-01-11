package com.bymason.kiosk.checkin.utils

import firebase.firestore.DocumentSnapshot
import firebase.functions.admin
import firebase.https.HttpsError
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.js.Json

suspend fun validateSession(uid: String, sessionId: String): DocumentSnapshot {
    val sessionDoc = admin.firestore().collection(uid)
            .doc("sessions")
            .collection("guest-visits")
            .doc(sessionId)
            .get()
            .await()

    if (!sessionDoc.exists || sessionDoc.data()["state"] == -1) {
        throw HttpsError("failed-precondition", "Session no longer exists or has been finalized.")
    }

    return sessionDoc
}

suspend fun fetchPopulatedSession(
        uid: String,
        sessionId: String
): Pair<DocumentSnapshot, Json> = coroutineScope {
    val sessionDoc = validateSession(uid, sessionId)
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

    sessionDoc to session
}
