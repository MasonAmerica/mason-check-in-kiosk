package support.bymason.kiosk.checkin.core.data

import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await
import support.bymason.kiosk.checkin.core.model.Company
import support.bymason.kiosk.checkin.core.model.GuestField
import support.bymason.kiosk.checkin.core.model.GuestFieldType
import support.bymason.kiosk.checkin.core.model.Host

interface CheckInApi {
    suspend fun getCompanyMetadata(): Company

    suspend fun getGuestFields(): List<GuestField>

    suspend fun updateSessionForCreate(guestFields: List<Map<String, String?>>): String

    suspend fun updateSessionForHereToSee(sessionId: String, hostId: String): String

    suspend fun updateSessionForFinalize(sessionId: String): String

    suspend fun findHosts(name: String): List<Host>

    suspend fun generateNdaLink(sessionId: String): String
}

class DefaultCheckInApi(
        private val dispatchers: DispatcherProvider
) : CheckInApi {
    override suspend fun getCompanyMetadata(): Company = dispatchers.default {
        val data = mapOf("operation" to "get-company-metadata")
        val result = Firebase.functions.getHttpsCallable("clientApi").call(data).await()

        @Suppress("UNCHECKED_CAST") val response = result.data as Map<String, String>
        Company(response.getValue("name"), response.getValue("logo"))
    }

    override suspend fun getGuestFields(): List<GuestField> = dispatchers.default {
        val data = mapOf("operation" to "get-guest-fields")
        val result = Firebase.functions.getHttpsCallable("clientApi").call(data).await()

        @Suppress("UNCHECKED_CAST") val response = result.data as List<Map<String, Any>>
        response.map {
            GuestField(
                    it.getValue("id") as String,
                    GuestFieldType.from(it.getValue("type") as Int),
                    it.getValue("name") as String,
                    it.getValue("required") as Boolean,
                    it["regex"] as String?
            )
        }
    }

    override suspend fun updateSessionForCreate(
            guestFields: List<Map<String, String?>>
    ): String = dispatchers.default {
        val data = mapOf("operation" to "session-create", "guestFields" to guestFields)
        val result = Firebase.functions.getHttpsCallable("clientApi").call(data).await()

        @Suppress("UNCHECKED_CAST") val response = result.data as Map<String, String>
        response.getValue("id")
    }

    override suspend fun updateSessionForHereToSee(
            sessionId: String,
            hostId: String
    ): String = dispatchers.default {
        val data = mapOf(
                "operation" to "session-here-to-see",
                "id" to sessionId,
                "hostId" to hostId
        )
        val result = Firebase.functions.getHttpsCallable("clientApi").call(data).await()

        @Suppress("UNCHECKED_CAST") val response = result.data as Map<String, String>
        response.getValue("id")
    }

    override suspend fun updateSessionForFinalize(
            sessionId: String
    ): String = dispatchers.default {
        val data = mapOf("operation" to "session-finalize", "id" to sessionId)
        val result = Firebase.functions.getHttpsCallable("clientApi").call(data).await()

        @Suppress("UNCHECKED_CAST") val response = result.data as Map<String, String>
        response.getValue("id")
    }

    override suspend fun findHosts(name: String): List<Host> = dispatchers.default {
        val data = mapOf("operation" to "session-find-hosts", "query" to name)
        val result = Firebase.functions.getHttpsCallable("clientApi").call(data).await()

        @Suppress("UNCHECKED_CAST") val response = result.data as List<Map<String, String>>
        response.map { Host(it.getValue("id"), it.getValue("name"), it["photoUrl"]) }
    }

    override suspend fun generateNdaLink(sessionId: String): String = dispatchers.default {
        val data = mapOf("operation" to "session-nda-link", "id" to sessionId)
        val result = Firebase.functions.getHttpsCallable("clientApi").call(data).await()

        @Suppress("UNCHECKED_CAST") val response = result.data as Map<String, String>
        response.getValue("url")
    }
}
