package shared.data.paging

import kotlinx.coroutines.CoroutineScope

/**
 * Interface for creating pagers that handle paginated data loading.
 * Provides functionality to create pagers with specific page loading strategies.
 */
interface PagingSource {

    /**
     * Creates a pager with the specified coroutine scope and page loader.
     *
     * @param scope The coroutine scope in which the pager will operate.
     * @param loader The loader responsible for loading pages of data.
     * @return A pager instance configured with the provided scope and loader.
     */
    fun <V : Any> createPager(scope: CoroutineScope, loader: PageLoader<V>): Pager<V>
}
