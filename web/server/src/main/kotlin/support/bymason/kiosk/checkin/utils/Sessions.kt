package support.bymason.kiosk.checkin.utils

import admin_directory_v1.`Schema$User`
import firebase.firestore.DocumentSnapshot
import firebase.functions.admin
import firebase.https.HttpsError
import google.google
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.js.Json
import kotlin.js.json

suspend fun fetchValidatedSession(uid: String, sessionId: String): DocumentSnapshot {
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
    val sessionDoc = fetchValidatedSession(uid, sessionId)
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

suspend fun fetchGsuiteHost(uid: String, hostId: String): `Schema$User` {
    val creds = getAndInitCreds(uid, "gsuite")
    val state = installGoogleAuth(creds.getValue("gsuite"))
    val directory = google.admin(json("version" to "directory_v1"))

    val host = directory.users.get(json(
            "viewType" to "domain_public",
            "userKey" to hostId
    )).await().data
    maybeRefreshGsuiteCreds(uid, state)

    return host
}
