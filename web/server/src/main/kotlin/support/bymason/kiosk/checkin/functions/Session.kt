package support.bymason.kiosk.checkin.functions

import firebase.firestore.DocumentSnapshot
import firebase.firestore.FieldValues
import firebase.firestore.SetOptions
import firebase.functions.AuthContext
import firebase.functions.admin
import firebase.https.HttpsError
import kotlinx.coroutines.await
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import support.bymason.kiosk.checkin.utils.fetchValidatedSession
import kotlin.js.Json
import kotlin.js.json

suspend fun createSession(auth: AuthContext, data: Json): Json {
    val guestFields = data["guestFields"] as? Array<Json>
            ?: throw HttpsError("invalid-argument")

    return createSession(auth, guestFields)
}

private suspend fun createSession(auth: AuthContext, guestFields: Array<Json>): Json {
    val fieldMap = guestFields.associate {
        it["id"] as String to it["value"]
    }
    val expectedFields = admin.firestore().collection(auth.uid)
            .doc("config")
            .collection("guest-fields")
            .get()
            .await()

    val validatedFields = expectedFields.docs.map {
        val field = it.data()
        val value = fieldMap[it.id]
        val hasError = when {
            field["regex"] == null -> false
            value == null -> field["required"] as Boolean
            else -> !Regex(field["regex"] as String).matches(value as String)
        }

        if (hasError) {
            throw HttpsError("invalid-argument", "Field ${field["name"]} is invalid.")
        }

        json(
                "id" to it.id,
                "type" to field["type"],
                "name" to field["name"],
                "value" to fieldMap[it.id]
        )
    }

    val sessionDoc = admin.firestore().collection(auth.uid)
            .doc("sessions")
            .collection("guest-visits")
            .doc()

    sessionDoc.set(json(
            "state" to 0,
            "timestamp" to FieldValues.serverTimestamp(),
            "guestFields" to validatedFields.toTypedArray()
    )).await()

    console.log("Created session '${sessionDoc.id}'")
    return json("id" to sessionDoc.id)
}

suspend fun hereToSee(auth: AuthContext, data: Json): Json {
    val sessionId = data["id"] as? String
            ?: throw HttpsError("invalid-argument")
    val hostId = data["hostId"] as? String
            ?: throw HttpsError("invalid-argument")

    val sessionDoc = fetchValidatedSession(auth.uid, sessionId)

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

    val sessionDoc = fetchValidatedSession(auth.uid, sessionId)
    val session = sessionDoc.data()
    val hostId = session.asDynamic().hereToSee[0]
    val guestName = (session["guestFields"] as Array<Json>).mapNotNull { field ->
        if (field["type"] == 0) field["value"] as String else null
    }.single()

    return finalizeSession(auth, sessionDoc, hostId, guestName)
}

private suspend fun finalizeSession(
        auth: AuthContext,
        sessionDoc: DocumentSnapshot,
        host: Json,
        guestName: String
): Json {
    coroutineScope {
        launch {
            sessionDoc.ref.set(json(
                    "state" to -1,
                    "timestamp" to FieldValues.serverTimestamp()
            ), SetOptions.merge).await()
        }

        launch {
            admin.firestore()
                    .collection("notifications")
                    .doc()
                    .set(json("uid" to auth.uid, "host" to host, "guestName" to guestName))
                    .await()
        }
    }

    return json("id" to sessionDoc.id)
}
