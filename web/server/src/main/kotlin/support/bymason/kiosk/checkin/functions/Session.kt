package support.bymason.kiosk.checkin.functions

import admin_directory_v1.`Schema$User`
import firebase.firestore.DocumentSnapshot
import firebase.firestore.FieldValues
import firebase.firestore.SetOptions
import firebase.functions.AuthContext
import firebase.functions.admin
import firebase.functions.functions
import firebase.https.HttpsError
import kotlinx.coroutines.await
import superagent.superagent
import support.bymason.kiosk.checkin.utils.fetchGsuiteHost
import support.bymason.kiosk.checkin.utils.fetchPopulatedSession
import support.bymason.kiosk.checkin.utils.getAndInitCreds
import support.bymason.kiosk.checkin.utils.validateSession
import kotlin.js.Json
import kotlin.js.json

suspend fun createSession(auth: AuthContext, data: Json): Json {
    val guestFields = data["guestFields"] as? Array<Json>
            ?: throw HttpsError("invalid-argument")

    return createSession(auth, guestFields)
}

private suspend fun createSession(auth: AuthContext, guestFields: Array<Json>): Json {
    val sessionDoc = admin.firestore().collection(auth.uid)
            .doc("sessions")
            .collection("guest-visits")
            .doc()

    sessionDoc.set(json(
            "state" to 0,
            "timestamp" to FieldValues.serverTimestamp(),
            "guestFields" to guestFields.map {
                json("id" to it["id"], "value" to it["value"])
            }.toTypedArray()
    )).await()

    console.log("Created session '${sessionDoc.id}'")
    return json("id" to sessionDoc.id)
}

suspend fun hereToSee(auth: AuthContext, data: Json): Json {
    val sessionId = data["id"] as? String
            ?: throw HttpsError("invalid-argument")
    val hostId = data["hostId"] as? String
            ?: throw HttpsError("invalid-argument")

    val sessionDoc = validateSession(auth.uid, sessionId)

    return hereToSee(sessionDoc, hostId)
}

private suspend fun hereToSee(
        sessionDoc: DocumentSnapshot,
        hostId: String
): Json {
    sessionDoc.ref.set(json(
            "state" to 1,
            "timestamp" to FieldValues.serverTimestamp(),
            "hereToSee" to arrayOf(json("gsuite" to hostId))
    ), SetOptions.merge).await()

    return json("id" to sessionDoc.id)
}

suspend fun finalizeSession(auth: AuthContext, data: Json): Json {
    val sessionId = data["id"] as? String
            ?: throw HttpsError("invalid-argument")

    val (sessionDoc, session) = fetchPopulatedSession(auth.uid, sessionId)
    val hostId = session.asDynamic().hereToSee[0].gsuite
    val guestName = (session["guestFields"] as Array<Json>).mapNotNull { field ->
        if (field["type"] == 0) field["value"] as String else null
    }.single()

    return finalizeSession(auth, sessionDoc, hostId, guestName)
}

private suspend fun finalizeSession(
        auth: AuthContext,
        sessionDoc: DocumentSnapshot,
        hostId: String,
        guestName: String
): Json {
    val host = fetchGsuiteHost(auth.uid, hostId)
    console.log("Host: ", JSON.stringify(host))

    notifyHost(auth, host, guestName)

    sessionDoc.ref.set(json(
            "state" to -1,
            "timestamp" to FieldValues.serverTimestamp()
    ), SetOptions.merge).await()

    return json("id" to sessionDoc.id)
}

private suspend fun notifyHost(auth: AuthContext, host: `Schema$User`, guestName: String) {
    val notificationsSnap = admin.firestore()
            .collection(auth.uid)
            .doc("config")
            .collection("metadata")
            .doc("notifications")
            .get()
            .await()

    val notifyOrder = notificationsSnap.data()["guestArrival"].unsafeCast<Array<Int>>()
    for (notifyType in notifyOrder) {
        try {
            when (notifyType) {
                1 -> notifyHostViaEmail()
                2 -> notifyHostViaSlack(auth, host, guestName)
                3 -> notifyHostViaSms(host, guestName)
            }
            break
        } catch (t: Throwable) {
            console.log(t)

            if (t.asDynamic().code == "failed-precondition") {
                continue
            } else {
                throw t
            }
        }
    }
}

private fun notifyHostViaEmail() {
    error("Email notifications are not yet available")
}

private suspend fun notifyHostViaSlack(auth: AuthContext, host: `Schema$User`, guestName: String) {
    val creds = getAndInitCreds(auth.uid, "slack")
    val hostEmail = host.primaryEmail
    val slackUser = superagent.get("https://slack.com/api/users.lookupByEmail")
            .query(json(
                    "token" to creds.getValue("slack")["access_token"],
                    "email" to hostEmail
            ))
            .await().body
    console.log("Slack user: ", slackUser)

    if (!slackUser["ok"].unsafeCast<Boolean>()) {
        throw HttpsError("failed-precondition", slackUser["error"].unsafeCast<String>())
    }

    val slackUserId = slackUser.asDynamic().user.id
    val slackMessage = superagent.post("https://slack.com/api/chat.postMessage")
            .query(json(
                    "token" to creds.getValue("slack")["access_token"],
                    "channel" to slackUserId,
                    "text" to "Your guest ($guestName) just arrived! \uD83D\uDC4B"
            ))
            .await().body
    console.log("Slack message: ", slackMessage)

    if (!slackMessage["ok"].unsafeCast<Boolean>()) {
        throw HttpsError("unknown", slackMessage["error"].unsafeCast<String>())
    }
}

private suspend fun notifyHostViaSms(host: `Schema$User`, guestName: String) {
    val hostPhone = host.phones.unsafeCast<Array<Json>?>()
            ?.sortedByDescending { if (it["primary"] == true) 1 else 0 }
            ?.map { it["value"] as String }
            ?.firstOrNull()
    if (hostPhone.isNullOrEmpty()) {
        throw HttpsError("failed-precondition", "Host has no phone numbers.")
    }

    val accountSid = functions.config().twilio.sid
    val userHash = functions.config().twilio.user_hash
    val sms = superagent.post("https://api.twilio.com/2010-04-01/Accounts/$accountSid/Messages.json")
            .set(json("Authorization" to "Basic $userHash"))
            .type("form")
            .send(json(
                    "To" to if ("+" in hostPhone) hostPhone else "+1$hostPhone",
                    "From" to "+12064830420",
                    "Body" to "Your guest ($guestName) just arrived! \uD83D\uDC4B"
            ))
            .await().body
    console.log("Twilio message: ", sms)

    if (sms["error_code"] != null) {
        throw HttpsError("unknown", sms["error_message"].unsafeCast<String>())
    }
}
