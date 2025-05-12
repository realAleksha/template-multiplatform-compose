package feature.navigation.basic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.Snapshot
import feature.common.BaseFeatureProvider
import feature.common.FeatureContext
import feature.navigation.api.NavigationFeature
import feature.navigation.api.NavigationItem
import feature.navigation.api.NavigationType
import feature.navigation.basic.presentation.NavigationProvider
import feature.navigation.basic.presentation.NavigationState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import shared.presentation.ui.container.DsNavigationItem

class BasicNavigationProvider(
    type: NavigationType = NavigationType.Adaptive,
    items: List<NavigationItem> = emptyList()
) : BaseFeatureProvider(), NavigationFeature {

    private val itemsState = mutableStateOf(items)
    private val typeState = mutableStateOf(type)
    private val state = NavigationMutableState()

    @Composable
    override fun provideUI(context: FeatureContext, content: @Composable (() -> Unit)) {
        LaunchedEffect(context) {
            snapshotFlow { itemsState.value }
                .map { items -> items.map { item -> map(context, item, context::setBackStack) } }
                .collect { items ->
                    val itemsById = items.associateBy(DsNavigationItem::id)
                    val selected = context.getCurrentBackStackId()?.let(itemsById::get)
                    Snapshot.withMutableSnapshot {
                        state.setNavigationItems(items)
                        state.setSelectedItem(selected)
                        state.setVisible(items.isNotEmpty())
                    }
                    context.getCurrentBackStackIdChanges()
                        .map(itemsById::get)
                        .collectLatest(state::setSelectedItem)
                }
        }
        NavigationProvider(
            type = typeState.value,
            content = content,
            state = state
        )
    }

    override suspend fun onReceiveAction(action: Action, context: FeatureContext) {
        when (action) {
            is SetItems -> itemsState.value = action.items
        }
    }

    override fun setItems(items: List<NavigationItem>) {
        onSendAction(SetItems(items))
    }

    override fun setType(type: NavigationType) {
        typeState.value = type
    }

    override fun setVisible(visible: Boolean) {
        state.setVisible(visible)
    }

    override fun isVisible(): Boolean {
        return state.visible ?: false
    }

    private fun map(context: FeatureContext, item: NavigationItem, onRoute: (Any) -> Unit) = DsNavigationItem(
        label = item.label,
        enabled = item.enabled,
        showLabel = item.showLabel,
        activeIcon = item.activeIcon,
        inactiveIcon = item.inactiveIcon,
        onClick = { onRoute(item.route) },
        id = context.getBackStackId(item.route)
    )

    private data class SetItems(val items: List<NavigationItem>) : Action

    private class NavigationMutableState() : NavigationState {

        override var items: List<DsNavigationItem> by mutableStateOf(emptyList())
        override var selected: DsNavigationItem? by mutableStateOf(null)
        override var visible: Boolean? by mutableStateOf(null)

        override fun setVisible(visible: Boolean) {
            this.visible = visible
        }

        override fun setNavigationItems(items: List<DsNavigationItem>) {
            this.items = items
        }

        override fun setSelectedItem(selected: DsNavigationItem?) {
            this.selected = selected
        }
    }
}