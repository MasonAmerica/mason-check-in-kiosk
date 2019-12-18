package com.bymason.kiosk.checkin.functions

import com.bymason.kiosk.checkin.utils.fetchPopulatedSession
import com.bymason.kiosk.checkin.utils.getAndInitCreds
import com.bymason.kiosk.checkin.utils.installGoogleAuth
import com.bymason.kiosk.checkin.utils.maybeRefreshGsuiteCreds
import firebase.firestore.FieldValues
import firebase.firestore.SetOptions
import firebase.functions.AuthContext
import firebase.functions.admin
import firebase.https.CallableContext
import firebase.https.HttpsError
import google.google
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import superagent.superagent
import kotlin.js.Json
import kotlin.js.Promise
import kotlin.js.json

fun updateSession(data: Json, context: CallableContext): Promise<String>? {
    val auth = context.auth ?: throw HttpsError("unauthenticated")
    val operation = data["operation"] as? String
    val sessionId = data["id"] as? String
    val hostId = data["hostId"] as? String
    val guestFields = data["guestFields"] as? Array<Json>
    console.log(
            "Processing '$operation' check-in operation for user '${auth.uid}' with args: ",
            JSON.stringify(data)
    )

    if (operation == null) {
        throw HttpsError("invalid-argument")
    }

    return GlobalScope.async {
        when (operation.toUpperCase().replace("-", "_")) {
            "CREATE" -> createSession(auth, guestFields)
            "HERE_TO_SEE" -> hereToSee(auth, sessionId, hostId)
            "FINALIZE" -> finalizeSession(auth, sessionId)
            else -> throw HttpsError("invalid-argument")
        }
    }.asPromise()
}

private suspend fun createSession(auth: AuthContext, guestFields: Array<Json>?): String {
    if (guestFields == null) {
        throw HttpsError("invalid-argument")
    }

    return createSession(auth, guestFields)
}

private suspend fun createSession(auth: AuthContext, guestFields: Array<Json>): String {
    val sessionDoc = admin.firestore().collection(auth.uid)
            .doc("sessions")
            .collection("visits")
            .doc()

    sessionDoc.set(json(
            "state" to 0,
            "timestamp" to FieldValues.serverTimestamp(),
            "guestFields" to guestFields.map {
                json("id" to it["id"], "value" to it["value"])
            }.toTypedArray()
    )).await()

    console.log("Created session '${sessionDoc.id}'")
    return sessionDoc.id
}

private suspend fun hereToSee(
        auth: AuthContext,
        sessionId: String?,
        hostId: String?
): String {
    if (sessionId == null || hostId == null) {
        throw HttpsError("invalid-argument")
    }

    return hereToSee(auth, sessionId, hostId)
}

private suspend fun hereToSee(
        auth: AuthContext,
        sessionId: String,
        hostId: String
): String {
    val sessionDoc = admin.firestore().collection(auth.uid)
            .doc("sessions")
            .collection("visits")
            .doc(sessionId)

    sessionDoc.set(json(
            "timestamp" to FieldValues.serverTimestamp(),
            "hereToSee" to arrayOf(json("gsuite" to hostId))
    ), SetOptions.merge).await()

    return sessionDoc.id
}

private suspend fun finalizeSession(auth: AuthContext, sessionId: String?): String {
    if (sessionId == null) {
        throw HttpsError("invalid-argument")
    }

    val session = fetchPopulatedSession(auth.uid, sessionId)
    val hostId = session.asDynamic().hereToSee[0].gsuite
    val guestName = (session["guestFields"] as Array<Json>).mapNotNull { field ->
        if (field["type"] == 0) field["value"] as String else null
    }.single()

    return finalizeSession(auth, sessionId, hostId, guestName)
}

private suspend fun finalizeSession(
        auth: AuthContext,
        sessionId: String,
        hostId: String,
        guestName: String
): String {
    val creds = getAndInitCreds(auth.uid, "gsuite", "slack")
    val state = installGoogleAuth(creds.getValue("gsuite"))
    val directory = google.admin(json("version" to "directory_v1"))
    val host = directory.users.get(json(
            "viewType" to "domain_public",
            "userKey" to hostId
    )).await().data
    maybeRefreshGsuiteCreds(auth.uid, state)
    console.log("Host: ", host)

    val hostEmail = host.primaryEmail
    val slackUser = superagent.get("https://slack.com/api/users.lookupByEmail")
            .query(json(
                    "token" to creds.getValue("slack")["access_token"],
                    "email" to hostEmail
            ))
            .await().body
    console.log("Slack user: ", slackUser)

    if (!slackUser["ok"].unsafeCast<Boolean>()) {
        throw HttpsError("not-found", slackUser["error"].unsafeCast<String>())
    }

    val slackUserId = slackUser.asDynamic().user.id
    val slackMessage = superagent.post("https://slack.com/api/chat.postMessage")
            .query(json(
                    "token" to creds.getValue("slack")["access_token"],
                    "channel" to slackUserId,
                    "text" to "Your guest ($guestName) just arrived! \uD83D\uDC4B"
            ))
            .await().body
    console.log("Slack message: ", slackUser)

    if (!slackMessage["ok"].unsafeCast<Boolean>()) {
        throw HttpsError("unknown", slackUser["error"].unsafeCast<String>())
    }

    val sessionDoc = admin.firestore().collection(auth.uid)
            .doc("sessions")
            .collection("visits")
            .doc(sessionId)

    sessionDoc.set(json(
            "state" to 1,
            "timestamp" to FieldValues.serverTimestamp()
    ), SetOptions.merge).await()

    return sessionDoc.id
}
