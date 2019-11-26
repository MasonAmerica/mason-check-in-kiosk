package com.bymason.kiosk.checkin.functions

import com.bymason.kiosk.checkin.utils.getAndInitCreds
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

fun findEmployees(employee: String, context: CallableContext): Promise<Array<Json>> {
    val auth = context.auth ?: throw HttpsError("unauthenticated")
    console.log("Searching for '$employee'")

    return GlobalScope.async { findEmployees(auth, employee) }.asPromise()
}

private suspend fun findEmployees(auth: AuthContext, employee: String): Array<Json> {
    getAndInitCreds(auth.uid)
    val directory = google.admin(json("version" to "directory_v1"))
    val employees = directory.users.list(json(
            "customer" to "my_customer",
            "viewType" to "domain_public",
            "query" to employee
    )).await().data

    return employees.users.orEmpty().map {
        json(
                "id" to it.id,
                "name" to it.name?.fullName,
                "photoUrl" to it.thumbnailPhotoUrl
        )
    }.toTypedArray()
}
