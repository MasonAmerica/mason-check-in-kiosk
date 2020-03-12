package support.bymason.kiosk.checkin.functions

import admin_directory_v1.`Schema$User`
import firebase.firestore.DocumentSnapshot
import firebase.functions.admin
import firebase.functions.functions
import firebase.https.HttpsError
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import superagent.superagent
import support.bymason.kiosk.checkin.utils.fetchGsuiteHost
import support.bymason.kiosk.checkin.utils.getAndInitCreds
import kotlin.js.Json
import kotlin.js.Promise
import kotlin.js.json

fun sendNotifications(notificationDoc: DocumentSnapshot): Promise<Any?> {
    val notification = notificationDoc.data()
    console.log("Sending notification: ", JSON.stringify(notification))

    val uid = notification["uid"] as? String ?: error("Missing uid")
    val hostData = notification["host"] ?: error("Missing host")
    val guestName = notification["guestName"] as? String ?: error("Missing guest name")

    return GlobalScope.async {
        val host = fetchGsuiteHost(uid, hostData.asDynamic().gsuite)

        try {
            notifyHost(uid, host, guestName)
        } catch (t: Throwable) {
            console.error(t)
        }

        notificationDoc.ref.delete().await()
    }.asPromise()
}

private suspend fun notifyHost(uid: String, host: `Schema$User`, guestName: String) {
    val notificationsSnap = admin.firestore()
            .collection(uid)
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
                2 -> notifyHostViaSlack(uid, host, guestName, true)
                3 -> notifyHostViaSms(host, guestName)
                4 -> notifyHostViaSlack(uid, host, guestName, false)
                else -> throw HttpsError(
                        "failed-precondition", "Unknown notification type: $notifyType")
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
    throw HttpsError("unimplemented", "Email notifications are not yet available")
}

private suspend fun notifyHostViaSlack(
        uid: String,
        host: `Schema$User`,
        guestName: String,
        requirePresence: Boolean
) {
    val creds = getAndInitCreds(uid, "slack")
    val token = creds.getValue("slack")["access_token"]
    val hostEmail = host.primaryEmail

    val slackUser = superagent.get("https://slack.com/api/users.lookupByEmail")
            .query(json(
                    "token" to token,
                    "email" to hostEmail
            ))
            .await().body
    console.log("Slack user: ", JSON.stringify(slackUser))

    if (!slackUser["ok"].unsafeCast<Boolean>()) {
        throw HttpsError("failed-precondition", slackUser["error"].unsafeCast<String>())
    }
    val slackUserId = slackUser.asDynamic().user.id

    if (requirePresence) {
        val userPresence = superagent.get("https://slack.com/api/users.getPresence")
                .query(json(
                        "token" to token,
                        "user" to slackUserId
                ))
                .await().body

        if (!userPresence["ok"].unsafeCast<Boolean>()) {
            throw HttpsError("failed-precondition", slackUser["error"].unsafeCast<String>())
        }
        if (userPresence["presence"] != "active") {
            throw HttpsError("failed-precondition", "Slack user is not present.")
        }
    }

    val slackMessage = superagent.post("https://slack.com/api/chat.postMessage")
            .query(json(
                    "token" to token,
                    "channel" to slackUserId,
                    "text" to "Your guest ($guestName) just arrived! :wave:"
            ))
            .await().body
    console.log("Slack message: ", JSON.stringify(slackMessage))

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
    val accountToken = functions.config().twilio.token
    val auth = accountSid + ":" + accountToken
    val sms = superagent
            .post("https://$auth@api.twilio.com/2010-04-01/Accounts/$accountSid/Messages.json")
            .type("form")
            .send(json(
                    "To" to if ("+" in hostPhone) hostPhone else "+1$hostPhone",
                    "From" to "+12064830420",
                    "Body" to "Your guest ($guestName) just arrived!"
            ))
            .await().body
    console.log("Twilio message: ", JSON.stringify(sms))

    if (sms["error_code"] != null) {
        throw HttpsError("unknown", sms["error_message"].unsafeCast<String>())
    }
}
