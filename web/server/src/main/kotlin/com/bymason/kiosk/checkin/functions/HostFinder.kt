package com.bymason.kiosk.checkin.functions

import com.bymason.kiosk.checkin.utils.getAndInitCreds
import com.bymason.kiosk.checkin.utils.installGoogleAuth
import com.bymason.kiosk.checkin.utils.maybeRefreshGsuiteCreds
import firebase.functions.AuthContext
import firebase.https.CallableContext
import firebase.https.HttpsError
import google.google
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlin.js.Json
import kotlin.js.Promise
import kotlin.js.json

fun findHosts(data: Any?, context: CallableContext): Promise<Array<Json>> {
    val auth = context.auth ?: throw HttpsError("unauthenticated")
    val hostName = data as? String
    console.log("Searching for host '$hostName' as user '${auth.uid}'")

    if (hostName == null) {
        throw HttpsError("invalid-argument")
    }

    return GlobalScope.async { findHosts(auth, hostName) }.asPromise()
}

private suspend fun findHosts(auth: AuthContext, hostName: String): Array<Json> {
    val creds = getAndInitCreds(auth.uid, "gsuite")
    val state = installGoogleAuth(creds.getValue("gsuite"))
    val directory = google.admin(json("version" to "directory_v1"))
    val hosts = directory.users.list(json(
            "customer" to "my_customer",
            "viewType" to "domain_public",
            "query" to hostName
    )).await().data
    maybeRefreshGsuiteCreds(auth.uid, state)
    console.log("Hosts: ", JSON.stringify(hosts.users))

    return hosts.users.orEmpty().map {
        val profilePic = if (it.thumbnailPhotoUrl.orEmpty().contains("/private")) {
            val hash = js("require('md5')")(it.primaryEmail?.toLowerCase())
            "https://www.gravatar.com/avatar/$hash?default=${it.thumbnailPhotoUrl}"
        } else {
            it.thumbnailPhotoUrl
        }

        json(
                "id" to it.id,
                "name" to it.name?.fullName,
                "photoUrl" to profilePic
        )
    }.toTypedArray()
}
