package support.bymason.kiosk.checkin.core.data

import android.content.Context
import com.google.common.hash.Hashing
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import support.bymason.kiosk.checkin.core.CrashLogger
import support.bymason.kiosk.checkin.core.MasonKiosk
import support.bymason.kiosk.checkin.core.logBreadcrumb
import java.io.File
import java.util.concurrent.TimeUnit

interface Cache {
    suspend fun <T> memoize(input: Input<T>, block: suspend () -> T): T

    class Input<T>(
            vararg val keys: String,
            val processedToRaw: (T) -> String,
            val rawToProcessed: (String) -> T
    )
}

/**
 * Caching mechanism that preserves freshness by always executing and caching the result of the
 * block. The cache won't be used if the TTL has expired.
 */
class FreshCache(
        private val dispatchers: DispatcherProvider,
        private val context: Context = MasonKiosk,
        private val ttl: Long = 7,
        private val ttlUnit: TimeUnit = TimeUnit.DAYS
) : Cache {
    private val cacheDir by lazy { File(context.cacheDir, "fresh") }

    override suspend fun <T> memoize(
            input: Cache.Input<T>,
            block: suspend () -> T
    ): T = dispatchers.default {
        val resultOp = GlobalScope.async { block() }

        val cacheKey = Hashing.sha256().newHasher()
                .apply { for (key in input.keys) putString(key, Charsets.UTF_8) }
                .hash()
        val cacheFile = File(cacheDir, cacheKey.toString())

        save(cacheFile, input, resultOp)
        val lastCached = System.currentTimeMillis() - cacheFile.lastModified()
        if (lastCached > ttlUnit.toMillis(ttl) || cacheFile.length() == 0L) {
            resultOp.await()
        } else {
            try {
                dispatchers.io {
                    input.rawToProcessed(cacheFile.readText())
                }
            } catch (e: Exception) {
                logBreadcrumb("Failed to unmarshal cached result", e)
                resultOp.await()
            }
        }
    }

    private suspend fun <T> save(
            cacheFile: File,
            input: Cache.Input<T>,
            resultOp: Deferred<T>
    ) = GlobalScope.launch(dispatchers.io + CrashLogger.handler) {
        val raw = input.processedToRaw(resultOp.await())
        cacheFile.safeCreateNewFile().writeText(raw)
    }
}
