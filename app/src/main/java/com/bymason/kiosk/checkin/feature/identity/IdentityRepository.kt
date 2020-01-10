package com.bymason.kiosk.checkin.feature.identity

import com.bymason.kiosk.checkin.core.data.Cache
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.data.FreshCache
import com.bymason.kiosk.checkin.core.model.Company
import com.bymason.kiosk.checkin.core.model.GuestField
import kotlinx.coroutines.invoke
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.list

interface IdentityRepository {
    suspend fun getCompanyMetadata(): Company

    suspend fun getGuestFields(): List<FieldState>

    suspend fun registerFields(fields: List<FieldState>): String
}

class DefaultIdentityRepository(
        private val dispatchers: DispatcherProvider,
        private val api: CheckInApi,
        private val cache: Cache = FreshCache(dispatchers)
) : IdentityRepository {
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

    override suspend fun getGuestFields(): List<FieldState> {
        val input = Cache.Input(
                keys = *arrayOf("getGuestFields"),
                processedToRaw = { json.stringify(GuestField.serializer().list, it) },
                rawToProcessed = { json.parse(GuestField.serializer().list, it) }
        )
        val fields = cache.memoize(input) {
            api.getGuestFields()
        }

        return dispatchers.default {
            fields.map { FieldState(it) }
        }
    }

    override suspend fun registerFields(fields: List<FieldState>): String {
        val processedFields = dispatchers.default {
            fields
                    .filter { it.value != null }
                    .map { mapOf("id" to it.field.id, "value" to it.value) }
        }
        return api.updateSession("create", null, "guestFields" to processedFields)
    }
}
