package shared.data.cache

/**
 * Interface representing a cache key for storing and retrieving data in the cache.
 *
 * @param T The type of the cached data.
 */
interface CacheKey<T> {

    /**
     * Time-to-live (TTL) for the cached data, in milliseconds.
     */
    val ttl: Long

    /**
     * Determines if the cached data associated with this key is considered immortal.
     * In terms of the API, immortality means that the cache entry initialization request
     * is not bound to the lifecycle of the component from which it is called.
     *
     * For example, if a cache entry initialization request is called from a View (Fragment/Activity),
     * it will be canceled once the view is destroyed.
     * When a key is immortal, the cache entry will be initialized regardless of the component lifecycle.
     *
     * This can be useful when you need to proceed with some request without interruptions.

     * Another example:
     *
     * an OAuth refresh token actualization response needs to be completed and stored locally
     * as an atomic action, so any further calls under OAuth authorization can continue with the newly obtained token.
     * If such an action is processed on the server but interrupted and not stored on the client,
     * it is possible that the old token becomes outdated and any further request to update it will fail.
     *
     * The immortal key helps reduce such issues by ensuring that cache entries are initialized
     * even if the associated component lifecycle ends.
     *
     * @return {@code true} if the data associated with this key is immortal, {@code false} otherwise.
     */
    fun immortal(): Boolean = false

    companion object {
        const val TTL_UNLIMITED = -1L
        const val TTL_1_SECOND = 1_000L
        const val TTL_3_SECONDS = 3_000L
        const val TTL_5_SECONDS = 5_000L
        const val TTL_10_SECONDS = 10_000L
        const val TTL_15_SECONDS = 15_000L
        const val TTL_30_SECONDS = 30_000L
        const val TTL_60_SECONDS = 60_000L
        const val TTL_5_MINUTES = 5 * 60_000L

        /**
         * Creates a new CacheKey with the specified time-to-live (TTL) duration.
         *
         * @param duration The time-to-live (TTL) duration for the cache key.
         * @param immortal Specifies whether the cache key is immortal or not. Defaults to false.
         *                 When set to true, the cache entry will not be bound to the lifecycle of the component
         *                 from which it is initialized.
         * @return A new CacheKey instance with the specified TTL duration and immortality status.
         */
        fun <T> of(duration: Long, immortal: Boolean = false): CacheKey<T> = object : CacheKey<T> {
            override val ttl: Long = duration
            override fun immortal(): Boolean = immortal
        }
    }

}