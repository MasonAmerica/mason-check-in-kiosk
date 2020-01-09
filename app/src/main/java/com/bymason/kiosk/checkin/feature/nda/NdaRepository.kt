package com.bymason.kiosk.checkin.feature.nda

import com.bymason.kiosk.checkin.core.data.CheckInApi

interface NdaRepository {
    suspend fun sign(sessionId: String): String

    suspend fun finish(sessionId: String)
}

class DefaultNdaRepository(
        private val api: CheckInApi
) : NdaRepository {
    override suspend fun sign(sessionId: String) = api.generateNdaLink(sessionId)

    override suspend fun finish(sessionId: String) {
        api.updateSession("finalize", sessionId)
    }
}
