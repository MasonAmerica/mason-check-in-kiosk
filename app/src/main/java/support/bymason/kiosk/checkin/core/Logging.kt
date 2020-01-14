package support.bymason.kiosk.checkin.core

import android.util.Log
import com.crashlytics.android.Crashlytics
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import support.bymason.kiosk.checkin.BuildConfig

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
    val handler = CoroutineExceptionHandler { _, t -> invoke(t) }

    override fun invoke(t: Throwable?) {
        if (t == null || t is CancellationException) return
        if (BuildConfig.DEBUG) {
            Log.e("CrashLogger", "An error occurred", t)
        } else {
            Crashlytics.logException(t)
        }
    }
}
