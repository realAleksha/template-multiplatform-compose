package shared.data.source.paging.jetpack

import androidx.paging.PagingSource
import androidx.paging.PagingState
import shared.data.source.paging.PageLoader

internal class JetpackPageLoader<V : Any>(
    private val loader: PageLoader<V>
) : PagingSource<Int, V>() {

    override fun getRefreshKey(state: PagingState<Int, V>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, V> {
        val offset = params.key ?: 0
        val limit = params.loadSize
        val items = loader.load(limit, offset)

        return LoadResult.Page(
            data = items,
            prevKey = (offset - limit).takeIf { it >= 0 },
            nextKey = (offset + items.size).takeIf { it != offset }
        )
    }
}