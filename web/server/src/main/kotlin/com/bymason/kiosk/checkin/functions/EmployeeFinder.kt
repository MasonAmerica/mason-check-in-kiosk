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

fun findEmployees(data: Any?, context: CallableContext): Promise<Array<Json>> {
    val auth = context.auth ?: throw HttpsError("unauthenticated")
    val employee = data as? String
    console.log("Searching for employee '$employee' as user '${auth.uid}'")

    if (employee == null) {
        throw HttpsError("invalid-argument")
    }

    return GlobalScope.async { findEmployees(auth, employee) }.asPromise()
}

private suspend fun findEmployees(auth: AuthContext, employee: String): Array<Json> {
    val creds = getAndInitCreds(auth.uid, "gsuite")
    val state = installGoogleAuth(creds.getValue("gsuite"))
    val directory = google.admin(json("version" to "directory_v1"))
    val employees = directory.users.list(json(
            "customer" to "my_customer",
            "viewType" to "domain_public",
            "query" to employee
    )).await().data
    maybeRefreshGsuiteCreds(auth.uid, state)
    console.log("Employees: ", employees.users)

    return employees.users.orEmpty().map {
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
