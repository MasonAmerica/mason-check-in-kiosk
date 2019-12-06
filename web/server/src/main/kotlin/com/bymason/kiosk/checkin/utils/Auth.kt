package com.bymason.kiosk.checkin.utils

import firebase.functions.admin
import firebase.functions.functions
import google.OAuth2Client
import google.google
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.js.Json
import kotlin.js.json

fun buildGoogleAuthClient(): OAuth2Client = createInstance(
        google.asDynamic().auth.OAuth2,
        functions.config().gsuite.client_id,
        functions.config().gsuite.client_secret,
        "https://mason-check-in-kiosk.firebaseapp.com/auth/gsuite/redirect"
)

suspend fun getAndInitCreds(
        uid: String,
        vararg integrations: String
): Map<String, Json> = coroutineScope {
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
        integration to creds.data()
    }
}
