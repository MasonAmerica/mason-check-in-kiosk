package com.bymason.kiosk.checkin.core

import android.util.Log
import com.bymason.kiosk.checkin.BuildConfig
import com.crashlytics.android.Crashlytics
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletionHandler
import retrofit2.HttpException

fun logBreadcrumb(message: String, throwable: Throwable? = null) {
    Crashlytics.log(message)
    if (BuildConfig.DEBUG) Log.d("Breadcrumbs", message)
    if (throwable is HttpException) {
        try {
            throwable.response()?.errorBody()?.string()
        } catch (e: Exception) {
            logBreadcrumb("Failed to parse error body", e)
            null
        }
    } else {
        null
    }?.let { logBreadcrumb(it) }

    CrashLogger.invoke(throwable)
}

object CrashLogger : CompletionHandler {
    override fun invoke(t: Throwable?) {
        if (t == null || t is CancellationException) return
        if (BuildConfig.DEBUG) {
            Log.e("CrashLogger", "An error occurred", t)
        } else {
            Crashlytics.logException(t)
        }
    }
}
