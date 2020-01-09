package com.bymason.kiosk.checkin.feature.identity

import com.bymason.kiosk.checkin.core.data.Cache
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.data.FreshCache
import com.bymason.kiosk.checkin.core.model.GuestField
import com.bymason.kiosk.checkin.core.model.GuestFieldType
import com.google.gson.Gson
import kotlinx.coroutines.invoke

interface IdentityRepository {
    suspend fun getGuestFields(): List<FieldState>

    suspend fun registerFields(fields: List<FieldState>): String
}

class DefaultIdentityRepository(
        private val dispatchers: DispatcherProvider,
        private val api: CheckInApi,
        private val cache: Cache = FreshCache(dispatchers)
) : IdentityRepository {
    private val gson = Gson()

    override suspend fun getGuestFields(): List<FieldState> {
        val input = Cache.Input(
                keys = *arrayOf("getGuestFields"),
                processedToRaw = { gson.toJson(it) },
                rawToProcessed = ::parseSerializedGuestFields
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

    @Suppress("UNCHECKED_CAST")
    private fun parseSerializedGuestFields(raw: String): List<GuestField> {
        val rawObjects = gson.fromJson(raw, List::class.java) as List<Map<String, Any>>
        return rawObjects.map {
            GuestField(
                    it.getValue("id") as String,
                    GuestFieldType.valueOf(it.getValue("type") as String),
                    it.getValue("name") as String,
                    it.getValue("required") as Boolean,
                    it["regex"] as String?
            )
        }
    }
}
