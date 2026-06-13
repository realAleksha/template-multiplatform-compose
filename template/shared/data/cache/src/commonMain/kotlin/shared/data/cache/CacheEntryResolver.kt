package shared.data.cache

/**
 * A functional interface for resolving cache entries.
 *
 * @param <T> the type of the value to be resolved from the cache.
 * @param <K> the type of the key used to resolve the value, which extends {@link CacheKey<T>}.
 */
fun interface CacheEntryResolver<T, K : CacheKey<T>> {

    /**
     * Resolves a value from the cache using the given key.
     *
     * @param key the key used to resolve the value.
     * @return the resolved value, or {@code null} if the value could not be resolved.
     * @throws Exception if an error occurs during resolution.
     */
    suspend fun resolve(key: K): T?

}