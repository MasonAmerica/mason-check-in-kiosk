package com.bymason.kiosk.checkin.feature.nda

import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await

interface NdaRepository {
    suspend fun sign(sessionId: String): String

    suspend fun finish(sessionId: String)
}

class DefaultNdaRepository : NdaRepository {
    override suspend fun sign(sessionId: String) = Default {
        Firebase.functions.getHttpsCallable("generateNdaLink")
                .call(sessionId)
                .await()
                .data as String
    }

    override suspend fun finish(sessionId: String) {
        Default {
            val data = mapOf(
                    "operation" to "finalize",
                    "id" to sessionId
            )
            Firebase.functions.getHttpsCallable("updateSession").call(data).await()
        }
    }
}
