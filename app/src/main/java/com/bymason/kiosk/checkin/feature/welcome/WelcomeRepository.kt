package com.bymason.kiosk.checkin.feature.welcome

import com.bymason.kiosk.checkin.core.data.Cache
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.data.FreshCache
import com.bymason.kiosk.checkin.core.model.Company
import com.google.gson.Gson

interface WelcomeRepository {
    suspend fun getCompanyMetadata(): Company
}

class DefaultWelcomeRepository(
        dispatchers: DispatcherProvider,
        private val api: CheckInApi,
        private val cache: Cache = FreshCache(dispatchers)
) : WelcomeRepository {
    private val gson = Gson()

    override suspend fun getCompanyMetadata(): Company {
        val input = Cache.Input(
                keys = *arrayOf("getCompanyMetadata"),
                processedToRaw = { gson.toJson(it) },
                rawToProcessed = { gson.fromJson(it, Company::class.java) }
        )
        return cache.memoize(input) {
            api.getCompanyMetadata()
        }
    }
}
