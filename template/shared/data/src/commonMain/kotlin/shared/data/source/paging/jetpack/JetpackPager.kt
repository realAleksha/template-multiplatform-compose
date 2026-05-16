package shared.data.source.paging.jetpack

import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import shared.data.source.paging.PageLoader
import shared.data.source.paging.Pager
import androidx.paging.Pager as JetpackPager

internal class JetpackPager<V : Any>(
    pageSize: Int,
    private val scope: CoroutineScope,
    private val loader: PageLoader<V>
) : Pager<V> {

    private var currentLoader: JetpackPageLoader<V>? = null

    private val pager by lazy {
        JetpackPager(
            config = PagingConfig(
                pageSize = pageSize,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                JetpackPageLoader(loader)
                    .also(this::currentLoader::set)
            }
        )
    }

    override fun pages(): Flow<Any> {
        return pager.flow.cachedIn(scope)
    }

    override fun refresh() {
        currentLoader?.invalidate()
    }
}