package shared.presentation.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState.Loading
import androidx.paging.LoadState.NotLoading
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import shared.data.paging.Pager

@Composable
fun <V : Any> DsPagingList(
    modifier: Modifier = Modifier.fillMaxSize(),
    pager: Pager<V>,
    itemKey: ((index: Int, item: V?) -> Any)? = null,
    itemContent: @Composable (item: V?) -> Unit,
    emptyContent: @Composable () -> Unit = {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "(^-^*)", fontSize = 24.sp, fontWeight = FontWeight.W600)
        }
    },
    refreshContent: @Composable () -> Unit = {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    },
    appendContent: @Composable () -> Unit = refreshContent
) {
    val pages = pager.pages() as? Flow<PagingData<V>>
    val items = pages?.collectAsLazyPagingItems()

    LazyColumn(
        modifier = modifier
    ) {
        val refreshState = items?.loadState?.refresh
        when {
            items == null -> {
                item { refreshContent() }
            }

            items.itemCount == 0 && refreshState is Loading -> {
                item { refreshContent() }
            }

            items.itemCount == 0 && refreshState is NotLoading -> {
                item { emptyContent() }
            }

            else -> {
                items(items.itemCount, key = itemKey?.let { { itemKey(it, items[it]) } }) { index ->
                    itemContent(items[index])
                }
                if (items.loadState.append is Loading) {
                    item { appendContent() }
                }
            }
        }
    }
}