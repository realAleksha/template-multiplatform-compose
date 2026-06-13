package shared.data.paging

import kotlinx.coroutines.flow.Flow

interface Pager<V : Any> {

    fun pages(): Flow<Any>

    fun refresh()
}