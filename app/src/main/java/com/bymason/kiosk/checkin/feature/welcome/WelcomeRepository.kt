package com.bymason.kiosk.checkin.feature.welcome

import com.bymason.kiosk.checkin.core.MasonKiosk
import com.bymason.kiosk.checkin.core.data.DefaultDispatcherProvider
import com.bymason.kiosk.checkin.core.data.DispatcherProvider
import com.bymason.kiosk.checkin.core.data.safeCreateNewFile
import com.bymason.kiosk.checkin.core.model.Company
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.invoke
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.concurrent.TimeUnit

interface WelcomeRepository {
    suspend fun getCompanyMetadata(): Company
}

class DefaultWelcomeRepository(
        private val dispatchers: DispatcherProvider = DefaultDispatcherProvider
) : WelcomeRepository {
    private val companyCache =
            File(MasonKiosk.applicationContext.cacheDir, "metadata/company.json")

    override suspend fun getCompanyMetadata(): Company = dispatchers.default {
        val cacheTtlHours = TimeUnit.MILLISECONDS.toHours(
                System.currentTimeMillis() - companyCache.lastModified())
        if (cacheTtlHours > CACHE_TTL_HOURS) {
            val company = loadRawCompanyMetadata()
            dispatchers.io {
                companyCache.safeCreateNewFile().writeText(Gson().toJson(company))
            }
            company
        } else {
            dispatchers.io {
                Gson().fromJson(companyCache.readText(), Company::class.java)
            }
        }
    }

    private suspend fun loadRawCompanyMetadata(): Company {
        val result = Firebase.functions.getHttpsCallable("getCompanyMetadata").call().await()
        @Suppress("UNCHECKED_CAST") val data = result.data as Map<String, String>
        return Company(data.getValue("name"), data.getValue("logo"))
    }

    private companion object {
        const val CACHE_TTL_HOURS = 72
    }
}
