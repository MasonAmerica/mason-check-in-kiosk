package com.bymason.kiosk.checkin.feature.welcome

import com.bymason.kiosk.checkin.core.data.Cache
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.data.FreshCache
import com.bymason.kiosk.checkin.core.model.Company
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

interface WelcomeRepository {
    suspend fun getCompanyMetadata(): Company
}

class DefaultWelcomeRepository(
        dispatchers: DispatcherProvider,
        private val api: CheckInApi,
        private val cache: Cache = FreshCache(dispatchers)
) : WelcomeRepository {
    private val json = Json(JsonConfiguration.Stable)

    override suspend fun getCompanyMetadata(): Company {
        val input = Cache.Input(
                keys = *arrayOf("getCompanyMetadata"),
                processedToRaw = { json.stringify(Company.serializer(), it) },
                rawToProcessed = { json.parse(Company.serializer(), it) }
        )
        return cache.memoize(input) {
            api.getCompanyMetadata()
        }
    }
}
