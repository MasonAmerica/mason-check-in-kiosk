package com.bymason.kiosk.checkin.utils

import firebase.functions.admin
import firebase.functions.functions
import google.OAuth2Client
import google.google
import kotlinx.coroutines.await
import kotlin.js.json

fun buildGoogleAuthClient(): OAuth2Client {
    @Suppress("UNUSED_VARIABLE") val clientId = functions.config().gsuite.client_id
    @Suppress("UNUSED_VARIABLE") val clientSecret = functions.config().gsuite.client_secret
    @Suppress("UNUSED_VARIABLE") val google = google
    return js("new google.auth.OAuth2(" +
                      "clientId, " +
                      "clientSecret, " +
                      "'https://mason-check-in-kiosk.firebaseapp.com/auth/gsuite/redirect'" +
                      ")").unsafeCast<OAuth2Client>()
}

suspend fun getAndInitCreds(uid: String): Credentials {
    val credsDoc = admin.firestore().collection("credentials").doc(uid).get().await()
    val gsuite = credsDoc.data()["gsuite"].asDynamic()
    val slack = credsDoc.data()["slack"].asDynamic()

    google.options(json("auth" to buildGoogleAuthClient().apply { credentials = gsuite }))

    return Credentials(gsuite.access_token, slack.access_token)
}

data class Credentials(val gsuiteToken: String, val slackToken: String)
