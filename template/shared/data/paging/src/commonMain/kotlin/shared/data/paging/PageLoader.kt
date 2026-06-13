package shared.data.paging

fun interface PageLoader<V> {

    suspend fun load(limit: Int, offset: Int): List<V>
}