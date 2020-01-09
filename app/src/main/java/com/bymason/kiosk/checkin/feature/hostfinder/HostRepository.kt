package com.bymason.kiosk.checkin.feature.hostfinder

import com.bymason.kiosk.checkin.core.data.Cache
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.data.FreshCache
import com.bymason.kiosk.checkin.core.model.Host
import com.google.gson.Gson

interface HostRepository {
    suspend fun find(name: String): List<Host>

    suspend fun registerHost(sessionId: String, host: Host): String
}

class DefaultHostRepository(
        dispatchers: DispatcherProvider,
        private val api: CheckInApi,
        private val cache: Cache = FreshCache(dispatchers)
) : HostRepository {
    private val gson = Gson()

    override suspend fun find(name: String): List<Host> {
        val input = Cache.Input(
                keys = *arrayOf("findHosts", name),
                processedToRaw = { gson.toJson(it) },
                rawToProcessed = ::parseSerializedHosts
        )
        return cache.memoize(input) {
            api.findHosts(name)
        }
    }

    override suspend fun registerHost(
            sessionId: String,
            host: Host
    ): String {
        return api.updateSession("here-to-see", sessionId, "hostId" to host.id)
    }

    @Suppress("UNCHECKED_CAST")
    private fun parseSerializedHosts(raw: String): List<Host> {
        val rawObjects = gson.fromJson(raw, List::class.java) as List<Map<String, Any>>
        return rawObjects.map {
            Host(
                    it.getValue("id") as String,
                    it.getValue("name") as String,
                    it["photoUrl"] as String?
            )
        }
    }
}
