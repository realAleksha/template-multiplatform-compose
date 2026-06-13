package shared.data.paging.jetpack

import kotlinx.coroutines.CoroutineScope
import shared.data.paging.PageLoader
import shared.data.paging.Pager
import shared.data.paging.PagingSource

class JetpackPagingSource(private val pageSize: Int = 30) : PagingSource {

    override fun <V : Any> createPager(scope: CoroutineScope, loader: PageLoader<V>): Pager<V> {
        return JetpackPager(pageSize, scope, loader)
    }
}