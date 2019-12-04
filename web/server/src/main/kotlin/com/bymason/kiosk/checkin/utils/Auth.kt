package com.bymason.kiosk.checkin.utils

import firebase.functions.admin
import firebase.functions.functions
import google.OAuth2Client
import google.google
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

suspend fun getAndInitCreds(
        uid: String,
        vararg integrations: String
): Map<String, String> = coroutineScope {
    val creds = integrations.map { integration ->
        async {
            integration to admin.firestore()
                    .collection("config")
                    .doc(uid)
                    .collection("credentials")
                    .doc(integration)
                    .get()
                    .await()
        }
    }.awaitAll()

    creds.find { (integration, _) -> integration == "gsuite" }?.let { (_, gsuiteCreds) ->
        google.options(json("auth" to buildGoogleAuthClient().apply {
            credentials = gsuiteCreds.data().asDynamic()
        }))
    }

    creds.associate { (integration, creds) ->
        integration to creds.data()["access_token"] as String
    }
}
