package shared.data.cache

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * Represents the state of a cache entry.
 *
 * @param T The type of the cached value.
 */
interface CacheEntry<T, K : CacheKey<T>> {

    /** The key associated with this cache entry. */
    val key: K

    /**
     * Sets the specified value to the given entry.
     *
     * @param value The value to be stored in the entry.
     */
    suspend fun set(value: T?)

    /**
     * Retrieves the cached value.
     *
     * @return The cached value, or new one if the value is not present in the cache or expired.
     */
    suspend fun get(): T?

    /**
     * Retrieves the last cached value.
     *
     * @return The last cached value, or null if the value is not present in the cache.
     */
    suspend fun last(): T?

    /**
     * Retrieves a fresh copy of the cached value.
     *
     * @return A fresh copy of the cached value, or null if the value is not available.
     */
    suspend fun fresh(): T?

    /**
     * Retrieves the last cached value if available, otherwise retrieves a fresh copy of the value.
     *
     * @return The last cached value if available, or a fresh copy of the value. Returns null if the value is not present in the cache.
     */
    suspend fun lastOrFresh() = last() ?: fresh()

    /**
     * Emits the cached value whenever it changes.
     * The flow updates a value in the cache based on the expiration of the key.
     *
     * @return A flow representing the changes to the cached value.
     */
    suspend fun changes(): Flow<T?>

    companion object {
        /**
         * Creates a CacheEntry instance representing a passed value.
         *
         * @param key The cache key associated with the value.
         * @param value The cached value.
         * @return A CacheState instance representing the single cached value.
         */
        fun <T, K : CacheKey<T>> of(key: K, value: T): CacheEntry<T, K> =
            object : CacheEntry<T, K> {
                private val valueChanges = MutableStateFlow<T?>(value)

                override val key: K = key
                override suspend fun get(): T? = value
                override suspend fun last(): T? = value
                override suspend fun fresh(): T? = value
                override suspend fun changes(): Flow<T?> = valueChanges
                override suspend fun set(value: T?) = valueChanges.update { value }
            }
    }

}