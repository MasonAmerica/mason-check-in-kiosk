package support.bymason.kiosk.checkin.feature.identity

import kotlinx.coroutines.invoke
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import support.bymason.kiosk.checkin.core.data.Cache
import support.bymason.kiosk.checkin.core.data.CheckInApi
import support.bymason.kiosk.checkin.core.data.DispatcherProvider
import support.bymason.kiosk.checkin.core.data.FreshCache
import support.bymason.kiosk.checkin.core.data.OneShotCache
import support.bymason.kiosk.checkin.core.model.Company
import support.bymason.kiosk.checkin.core.model.GuestField
import java.util.UUID

interface IdentityRepository {
    suspend fun getCompanyMetadata(): Company

    suspend fun getGuestFields(): List<FieldState>

    suspend fun registerFields(fields: List<FieldState>): String
}

class DefaultIdentityRepository(
        private val dispatchers: DispatcherProvider,
        private val api: CheckInApi,
        private val cache: Cache = FreshCache(dispatchers),
        private val oneShotCache: Cache = OneShotCache(dispatchers)
) : IdentityRepository {
    private val json = Json(JsonConfiguration.Stable)
    private val instanceId = UUID.randomUUID()

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
        val cacheKeys = dispatchers.default {
            val staticKeys = listOf("registerFields", instanceId.toString())
            val dynamicKeys = fields.flatMap { listOfNotNull(it.field.id, it.value) }

            (staticKeys + dynamicKeys).toTypedArray()
        }
        val input = Cache.Input(
                keys = *cacheKeys,
                processedToRaw = { it },
                rawToProcessed = { it }
        )

        return oneShotCache.memoize(input) {
            val processedFields = fields
                    .filter { it.value != null }
                    .map { mapOf("id" to it.field.id, "value" to it.value) }
            api.updateSessionForCreate(processedFields)
        }
    }
}
