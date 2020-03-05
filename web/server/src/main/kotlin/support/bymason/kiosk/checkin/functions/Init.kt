package support.bymason.kiosk.checkin.functions

import firebase.auth.UserInfo
import firebase.firestore.SetOptions
import firebase.firestore.WriteResult
import firebase.functions.admin
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.js.Promise
import kotlin.js.json

fun initUser(user: UserInfo): Promise<*>? = GlobalScope.async {
    console.log("Initializing user: ${JSON.stringify(user.toJSON())}")

    awaitAll(
            async { createDefaultGuestFields(user) },
            async { createDefaultNotificationPreferences(user) }
    )
}.asPromise()

private suspend fun createDefaultGuestFields(user: UserInfo) = coroutineScope {
    awaitAll(
            async { createGuestNameField(user) },
            async { createGuestEmailField(user) },
            async { createGuestCompanyField(user) }
    )
}

private suspend fun createDefaultNotificationPreferences(user: UserInfo): WriteResult {
    return admin.firestore()
            .collection(user.uid)
            .doc("config")
            .collection("metadata")
            .doc("notifications")
            .set(json(
                    "guestArrival" to arrayOf(2, 3, 4)
            ), SetOptions.merge)
            .await()
}

private suspend fun createGuestNameField(user: UserInfo): WriteResult {
    return admin.firestore().collection(user.uid)
            .doc("config")
            .collection("guest-fields")
            .doc()
            .create(json(
                    "name" to "Full name",
                    "position" to 0,
                    "regex" to ".+",
                    "required" to true,
                    "type" to 0
            ))
            .await()
}

private suspend fun createGuestEmailField(user: UserInfo): WriteResult {
    return admin.firestore().collection(user.uid)
            .doc("config")
            .collection("guest-fields")
            .doc()
            .create(json(
                    "name" to "Email address",
                    "position" to 1,
                    "regex" to "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@" +
                            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+",
                    "required" to true,
                    "type" to 1
            ))
            .await()
}

private suspend fun createGuestCompanyField(user: UserInfo): WriteResult {
    return admin.firestore().collection(user.uid)
            .doc("config")
            .collection("guest-fields")
            .doc()
            .create(json(
                    "name" to "Company name",
                    "position" to 2,
                    "regex" to ".+",
                    "required" to false,
                    "type" to 2
            ))
            .await()
}
