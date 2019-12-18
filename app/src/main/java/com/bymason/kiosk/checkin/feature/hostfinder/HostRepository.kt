package com.bymason.kiosk.checkin.feature.hostfinder

import com.bymason.kiosk.checkin.core.data.DefaultDispatcherProvider
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.model.Host
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await

interface HostRepository {
    suspend fun find(name: String): List<Host>

    suspend fun registerHost(sessionId: String, host: Host): String
}

class DefaultHostRepository(
        private val dispatchers: DispatcherProvider = DefaultDispatcherProvider
) : HostRepository {
    override suspend fun find(name: String): List<Host> = dispatchers.default {
        val result = Firebase.functions.getHttpsCallable("findHosts").call(name).await()
        @Suppress("UNCHECKED_CAST") val data = result.data as List<Map<String, String>>
        data.map { Host(it.getValue("id"), it.getValue("name"), it.getValue("photoUrl")) }
    }

    override suspend fun registerHost(
            sessionId: String,
            host: Host
    ) = dispatchers.default {
        val data = mapOf(
                "operation" to "here-to-see",
                "id" to sessionId,
                "hostId" to host.id
        )
        val result = Firebase.functions.getHttpsCallable("updateSession").call(data).await()
        result.data as String
    }
}
