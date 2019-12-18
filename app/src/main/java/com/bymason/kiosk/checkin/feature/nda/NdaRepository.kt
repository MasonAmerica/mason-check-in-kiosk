package com.bymason.kiosk.checkin.feature.nda

import com.bymason.kiosk.checkin.core.data.DefaultDispatcherProvider
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await

interface NdaRepository {
    suspend fun sign(sessionId: String): String

    suspend fun finish(sessionId: String)
}

class DefaultNdaRepository(
        private val dispatchers: DispatcherProvider = DefaultDispatcherProvider
) : NdaRepository {
    override suspend fun sign(sessionId: String) = dispatchers.default {
        Firebase.functions.getHttpsCallable("generateNdaLink")
                .call(sessionId)
                .await()
                .data as String
    }

    override suspend fun finish(sessionId: String) {
        dispatchers.default {
            val data = mapOf(
                    "operation" to "finalize",
                    "id" to sessionId
            )
            Firebase.functions.getHttpsCallable("updateSession").call(data).await()
        }
    }
}
