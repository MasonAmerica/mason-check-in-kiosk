package support.bymason.kiosk.checkin.core.data

import android.content.Context
import com.google.common.hash.Hashing
import kotlinx.coroutines.CoroutineStart
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
) : Cache by CacheImpl(dispatchers, context, ttl, ttlUnit, "fresh", true)

/** Caching mechanism that only executes the request once until the TTL expires. */
class OneShotCache(
        private val dispatchers: DispatcherProvider,
        private val context: Context = MasonKiosk,
        private val ttl: Long = 1,
        private val ttlUnit: TimeUnit = TimeUnit.HOURS
) : Cache by CacheImpl(dispatchers, context, ttl, ttlUnit, "one-shot", false)

private class CacheImpl(
        private val dispatchers: DispatcherProvider,
        private val context: Context,
        private val ttl: Long,
        private val ttlUnit: TimeUnit,
        private val cacheName: String,
        private val alwaysDoRequest: Boolean
) : Cache {
    private val cacheDir by lazy { File(context.cacheDir, cacheName) }

    override suspend fun <T> memoize(
            input: Cache.Input<T>,
            block: suspend () -> T
    ): T = dispatchers.default {
        val resultOp = GlobalScope.async(dispatchers.default, CoroutineStart.LAZY) { block() }
        if (alwaysDoRequest) resultOp.start()

        val cacheKey = Hashing.sha256().newHasher()
                .apply { for (key in input.keys) putString(key, Charsets.UTF_8) }
                .hash()
        val cacheFile = File(cacheDir, cacheKey.toString())

        val lastCached = System.currentTimeMillis() - cacheFile.lastModified()
        if (lastCached > ttlUnit.toMillis(ttl) || cacheFile.length() == 0L) {
            save(cacheFile, input, resultOp)
            resultOp.await()
        } else {
            try {
                if (alwaysDoRequest) save(cacheFile, input, resultOp)
                dispatchers.io {
                    input.rawToProcessed(cacheFile.readText())
                }
            } catch (e: Exception) {
                logBreadcrumb("Failed to unmarshal cached result", e)
                save(cacheFile, input, resultOp)
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
