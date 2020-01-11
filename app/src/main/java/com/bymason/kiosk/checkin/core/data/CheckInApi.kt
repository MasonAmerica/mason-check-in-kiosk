package com.bymason.kiosk.checkin.core.data

import com.bymason.kiosk.checkin.core.model.Company
import com.bymason.kiosk.checkin.core.model.GuestField
import com.bymason.kiosk.checkin.core.model.GuestFieldType
import com.bymason.kiosk.checkin.core.model.Host
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await

interface CheckInApi {
    suspend fun getCompanyMetadata(): Company

    suspend fun getGuestFields(): List<GuestField>

    suspend fun updateSession(
            operation: String,
            sessionId: String?,
            vararg params: Pair<String, Any>
    ): String

    suspend fun findHosts(name: String): List<Host>

    suspend fun generateNdaLink(sessionId: String): String
}

class DefaultCheckInApi(
        private val dispatchers: DispatcherProvider
) : CheckInApi {
    override suspend fun getCompanyMetadata(): Company = dispatchers.default {
        val result = Firebase.functions.getHttpsCallable("getCompanyMetadata").call().await()
        @Suppress("UNCHECKED_CAST") val data = result.data as Map<String, String>
        Company(data.getValue("name"), data.getValue("logo"))
    }

    override suspend fun getGuestFields(): List<GuestField> = dispatchers.default {
        val result = Firebase.functions.getHttpsCallable("getGuestFields").call().await()
        @Suppress("UNCHECKED_CAST") val data = result.data as List<Map<String, Any>>
        data.map {
            GuestField(
                    it.getValue("id") as String,
                    GuestFieldType.from(it.getValue("type") as Int),
                    it.getValue("name") as String,
                    it.getValue("required") as Boolean,
                    it["regex"] as String?
            )
        }
    }

    override suspend fun updateSession(
            operation: String,
            sessionId: String?,
            vararg params: Pair<String, Any>
    ): String = dispatchers.default {
        val data = mutableMapOf<String, Any>("operation" to operation)
        sessionId?.let { data["id"] = it }
        for ((key, value) in params) data[key] = value

        val result = Firebase.functions.getHttpsCallable("updateSession").call(data).await()
        @Suppress("UNCHECKED_CAST") val response = result.data as Map<String, String>
        response.getValue("id")
    }

    override suspend fun findHosts(name: String): List<Host> = dispatchers.default {
        val result = Firebase.functions.getHttpsCallable("findHosts").call(name).await()
        @Suppress("UNCHECKED_CAST") val data = result.data as List<Map<String, String>>
        data.map { Host(it.getValue("id"), it.getValue("name"), it["photoUrl"]) }
    }

    override suspend fun generateNdaLink(sessionId: String): String = dispatchers.default {
        val result = Firebase.functions.getHttpsCallable("generateNdaLink").call(sessionId).await()
        @Suppress("UNCHECKED_CAST") val response = result.data as Map<String, String>
        response.getValue("url")
    }
}
