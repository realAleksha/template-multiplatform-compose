package shared.data.source.cache

import co.touchlab.stately.collections.ConcurrentMutableMap
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import shared.data.misc.isCancellationException
import kotlin.reflect.KClass
import kotlin.time.Clock

/**
 * Basic implementation of a thread-safe cache for storing and retrieving in-memory data.
 * This source can be utilized as an L1 Cache when managing HTTP requests, offering an efficient means
 * to present data without delays, but with the ability to update based on expiration and other conditions.
 *
 * @param changesRetryInterval The interval, in milliseconds, to retry cache changes.
 * @param exceptionRetryCount The maximum number of retries for cache operations in case of exceptions.
 */
@Suppress("UNCHECKED_CAST")
data class BasicCacheSource(
    private val changesRetryInterval: Long = 1000L,
    private val exceptionRetryInterval: Long = 1000L,
    private val exceptionRetryCount: Int = 10
) : CacheSource {

    private val dispatcher = Dispatchers.Default
    private val jobs = ConcurrentMutableMap<KeyData<*>, Deferred<*>>()
    private val cache = ConcurrentMutableMap<KeyData<*>, EntryData<*, *>>()

    override fun <T, K : CacheKey<T>> get(
        key: K,
        resolver: CacheEntryResolver<T, K>
    ): CacheEntry<T, K> {
        val keyData = KeyData(key)
        val entryData = cache.computeIfAbsent(keyData) {
            EntryData(keyData, resolver)
        } as CacheEntry<T, K>
        return entryData
    }

    override fun clear() {
        jobs.onEach { job -> job.value.cancel() }
        jobs.clear()
        cache.clear()
    }

    override fun <K : CacheKey<*>> invalidate(type: KClass<K>) {
        val jobsIterator = jobs.iterator()
        while (jobsIterator.hasNext()) {
            val entry = jobsIterator.next()
            val key = entry.key
            if (key.type == type) {
                jobsIterator.remove()
                entry.value.cancel()
            }
        }
        val cacheIterator = cache.iterator()
        while (cacheIterator.hasNext()) {
            val entry = cacheIterator.next()
            if (entry.key.type == type) {
                entry.value.invalidate()
            }
        }
    }

    override fun <K : CacheKey<*>> invalidate(key: K) {
        val cacheKey = KeyData(key)
        jobs.remove(cacheKey)?.cancel()
        cache[cacheKey]?.invalidate()
    }

    override fun <K : CacheKey<*>> remove(type: KClass<K>) {
        val jobsIterator = jobs.iterator()
        while (jobsIterator.hasNext()) {
            val entry = jobsIterator.next()
            val key = entry.key
            if (key.type == type) {
                jobsIterator.remove()
                entry.value.cancel()
            }
        }
        val cacheIterator = cache.iterator()
        while (cacheIterator.hasNext()) {
            val entry = cacheIterator.next()
            if (entry.key.type == type) {
                cacheIterator.remove()
                entry.value.invalidate()
            }
        }
    }

    override fun <K : CacheKey<*>> remove(key: K) {
        val cacheKey = KeyData(key)
        jobs.remove(cacheKey)?.cancel()
        cache.remove(cacheKey)
    }

    private data class KeyData<K : CacheKey<*>>(
        val key: K,
        val type: KClass<*> = key::class
    )

    private data class EntrySnapshot<T>(
        val value: T,
        val updateTime: Long = Clock.System.now().toEpochMilliseconds()
    )

    private inner class EntryData<T, K : CacheKey<T>>(
        private val keyData: KeyData<K>,
        private val resolver: CacheEntryResolver<T, K>
    ) : CacheEntry<T, K> {

        private var invalidated = false
        private val liveChanges by lazy { fetchLiveChanges() }
        private val changes = MutableStateFlow<EntrySnapshot<T>?>(null)

        override val key: K = keyData.key

        override suspend fun set(value: T?) = changes.update { value?.let(::EntrySnapshot) }

        override suspend fun fresh(): T? = invalidate().run { get() }

        override suspend fun last(): T? = changes.value?.value

        override suspend fun get(): T? {
            return if (!isValid(key.ttl)) {
                val newValue = fetchValue()
                changes.updateAndGet { newValue?.let(::EntrySnapshot) }?.value
            } else {
                changes.value?.value
            }
        }

        override suspend fun changes(): Flow<T?> = liveChanges
            .flatMapLatest { changes }
            .map { snapshot -> snapshot?.value }
            .retry { th -> !th.isCancellationException().also { delay(changesRetryInterval) } }

        fun invalidate() {
            invalidated = true
        }

        private fun isValid(ttl: Long): Boolean = when {
            invalidated -> false
            changes.value == null -> false
            ttl > 0 -> !isExpired(ttl)
            ttl == 0L -> false
            else -> true
        }

        private fun isExpired(ttl: Long): Boolean {
            val updateTime = changes.value?.updateTime ?: return true
            val now = Clock.System.now().toEpochMilliseconds()
            return ttl > 0 && updateTime + ttl <= now
        }

        private suspend fun fetchValue(): T? {
            val job = jobs[keyData]
                ?.let { deferredJob ->
                    if (deferredJob.isCancelled) {
                        jobs.remove(keyData)?.let { null }
                    } else {
                        deferredJob
                    }
                }
                ?: run {
                    if (key.immortal()) {
                        jobs.computeIfAbsent(keyData) {
                            GlobalScope.async { resolver.resolve(key) }
                        }
                    } else {
                        withContext(dispatcher) {
                            jobs.computeIfAbsent(keyData) {
                                async { resolver.resolve(key) }
                            }
                        }
                    }
                }
            job.invokeOnCompletion { jobs.remove(keyData)?.let {} }

            return job.await() as? T
        }

        private fun fetchLiveChanges() = flow {
            emit(true)
            var retryAttempt = 0
            while (currentCoroutineContext().isActive) {
                runCatching {
                    get()
                    val updateTime = changes.value?.updateTime

                    if (updateTime == null) {
                        delay(changesRetryInterval)
                    } else {
                        val now = Clock.System.now().toEpochMilliseconds()
                        val time = key.ttl - (now - updateTime)
                        delay(time)
                    }

                    retryAttempt = 0
                }.onFailure { th ->
                    retryAttempt++
                    when {
                        retryAttempt >= exceptionRetryCount -> throw th
                        else -> delay(exceptionRetryInterval)
                    }
                }
            }
        }.shareIn(GlobalScope, SharingStarted.WhileSubscribed(), 1)
    }

}