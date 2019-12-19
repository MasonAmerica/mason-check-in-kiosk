package com.bymason.kiosk.checkin.feature.identity

import com.bymason.kiosk.checkin.core.MasonKiosk
import com.bymason.kiosk.checkin.core.data.DefaultDispatcherProvider
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.data.safeCreateNewFile
import com.bymason.kiosk.checkin.core.model.GuestField
import com.bymason.kiosk.checkin.core.model.GuestFieldType
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.concurrent.TimeUnit

interface IdentityRepository {
    suspend fun getGuestFields(): List<FieldState>

    suspend fun registerFields(fields: List<FieldState>): String
}

class DefaultIdentityRepository(
        private val dispatchers: DispatcherProvider = DefaultDispatcherProvider
) : IdentityRepository {
    private val fieldsCache =
            File(MasonKiosk.applicationContext.cacheDir, "identity/guest-fields.json")

    @Suppress("UNCHECKED_CAST")
    override suspend fun getGuestFields(): List<FieldState> = dispatchers.default {
        val cacheTtlHours = TimeUnit.MILLISECONDS.toHours(
                System.currentTimeMillis() - fieldsCache.lastModified())
        if (cacheTtlHours > CACHE_TTL_HOURS) {
            val fields = loadRawGuestFields()
            dispatchers.io {
                fieldsCache.safeCreateNewFile().writeText(Gson().toJson(fields))
            }
            fields
        } else {
            dispatchers.io {
                Gson().fromJson(fieldsCache.readText(), List::class.java) as List<Map<String, Any>>
            }
        }.map {
            GuestField(
                    it.getValue("id") as String,
                    GuestFieldType.from(it.getValue("type") as? Int
                                                ?: (it.getValue("type") as Double).toInt()),
                    it.getValue("name") as String,
                    it.getValue("required") as Boolean,
                    it.getValue("regex") as String
            )
        }.map {
            FieldState(it)
        }
    }

    override suspend fun registerFields(fields: List<FieldState>) = dispatchers.default {
        val processedFields = fields
                .filter { it.value != null }
                .map { mapOf("id" to it.field.id, "value" to it.value) }
        val data = mapOf(
                "operation" to "create",
                "guestFields" to processedFields
        )

        val result = Firebase.functions.getHttpsCallable("updateSession").call(data).await()
        result.data as String
    }

    private suspend fun loadRawGuestFields(): List<Map<String, Any>> {
        val result = Firebase.functions.getHttpsCallable("getGuestFields").call().await()
        return result.data as List<Map<String, Any>>
    }

    private companion object {
        const val CACHE_TTL_HOURS = 32
    }
}
