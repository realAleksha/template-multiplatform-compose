package shared.data.cache

import shared.data.common.DataSource
import kotlin.reflect.KClass

/**
 * Provides an interface for a basic thread-safe caching mechanism, serving as an L1 Cache.
 *
 * The cache allows for storing and retrieving any in-memory data efficiently.
 *
 * It supports operations such as getting, putting, removing, and invalidating cache entries.
 */
interface CacheSource : DataSource {

    /**
     * Retrieves the cache entry associated with the specified key.
     * If the entry is not found in the cache, the provided value provider function is invoked to obtain the value.
     *
     * @param key The cache key associated with the entry.
     * @param resolver A suspend function that provides the value if the cache entry is not found.
     * @return A CacheState object representing the state of the cache entry.
     */
    fun <T, K : CacheKey<T>> get(key: K, resolver: CacheEntryResolver<T, K>): CacheEntry<T, K>

    /**
     * Invalidates all cache entries associated with the specified key type.
     *
     * @param type The type of cache keys to invalidate.
     */
    fun <K : CacheKey<*>> invalidate(type: KClass<K>)

    /**
     * Invalidates the cache entry associated with the specified key.
     *
     * @param key The cache key to invalidate.
     */
    fun <K : CacheKey<*>> invalidate(key: K)

    /**
     * Removes all cache entries associated with the specified key type.
     *
     * @param type The type of cache keys to remove.
     */
    fun <K : CacheKey<*>> remove(type: KClass<K>)

    /**
     * Removes the cache entry associated with the specified key.
     *
     * @param key The cache key to remove.
     */
    fun <K : CacheKey<*>> remove(key: K)

    /**
     * Clears all entries from the cache.
     */
    fun clear()

}