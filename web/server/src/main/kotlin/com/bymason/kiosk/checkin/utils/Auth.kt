package com.bymason.kiosk.checkin.utils

import firebase.firestore.SetOptions
import firebase.functions.admin
import firebase.functions.functions
import google.OAuth2Client
import google.google
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import superagent.superagent
import kotlin.js.Json
import kotlin.js.json

fun buildGoogleAuthClient(): OAuth2Client = createInstance(
        google.asDynamic().auth.OAuth2,
        functions.config().gsuite.client_id,
        functions.config().gsuite.client_secret,
        "https://mason-check-in-kiosk.firebaseapp.com/redirect/gsuite/auth"
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

    creds.associate { (integration, creds) ->
        integration to creds.data()
    }
}

fun installGoogleAuth(creds: Json): GoogleAuthState {
    val client = buildGoogleAuthClient().apply {
        credentials = creds.asDynamic()
    }
    google.options(json("auth" to client))
    return GoogleAuthState(client.credentials.access_token, client)
}

fun maybeRefreshGsuiteCreds(uid: String, state: GoogleAuthState) {
    if (state.prevToken == state.client.credentials.access_token) return

    console.log("Soft refreshing gsuite token")
    admin.firestore()
            .collection("config")
            .doc(uid)
            .collection("credentials")
            .doc("gsuite")
            .set(state.client.credentials.asDynamic(), SetOptions.merge)
}

suspend fun refreshDocusignCreds(uid: String, creds: Json): Json {
    console.log("Soft refreshing docusign token")
    val credsResult = superagent.post("https://account-d.docusign.com/oauth/token")
            .set(json("Authorization" to "Basic ${functions.config().docusign.client_hash}"))
            .query(json(
                    "grant_type" to "refresh_token",
                    "refresh_token" to creds["refresh_token"]
            )).await()

    admin.firestore()
            .collection("config")
            .doc(uid)
            .collection("credentials")
            .doc("docusign")
            .set(credsResult.body, SetOptions.merge)

    return credsResult.body
}

data class GoogleAuthState(val prevToken: String?, val client: OAuth2Client)
