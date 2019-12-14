package com.bymason.kiosk.checkin.feature.identity

import com.bymason.kiosk.checkin.core.model.GuestField
import com.bymason.kiosk.checkin.core.model.GuestFieldType
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await

interface IdentityRepository {
    suspend fun getGuestFields(): List<FieldState>

    suspend fun registerFields(fields: List<FieldState>): String
}

class DefaultIdentityRepository : IdentityRepository {
    @Suppress("UNCHECKED_CAST")
    override suspend fun getGuestFields(): List<FieldState> = Default {
        val result = Firebase.functions.getHttpsCallable("getGuestFields").call().await()
        val data = result.data as List<Map<String, Any>>
        data.map {
            GuestField(
                    it.getValue("id") as String,
                    GuestFieldType.from(it.getValue("type") as Int),
                    it.getValue("name") as String,
                    it.getValue("required") as Boolean,
                    it.getValue("regex") as String
            )
        }.map {
            FieldState(it)
        }
    }

    override suspend fun registerFields(fields: List<FieldState>) = Default {
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
}
