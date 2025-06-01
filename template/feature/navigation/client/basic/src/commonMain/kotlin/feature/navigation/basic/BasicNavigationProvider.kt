package feature.navigation.basic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.Snapshot
import androidx.navigation.NavGraphBuilder
import feature.common.api.FeatureContext
import feature.common.koin.KoinFeatureProvider
import feature.common.preview.FeatureMethod
import feature.common.preview.FeaturePreviewProvider
import feature.common.preview.method.MethodCallsAction
import feature.common.preview.method.MethodChangesOption
import feature.common.preview.method.MethodReturnsValue
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
) : KoinFeatureProvider(), FeaturePreviewProvider, NavigationFeature {

    private val itemsState = mutableStateOf(items)
    private val typeState = mutableStateOf(type)
    private val state = NavigationMutableState()

    override val name: String = "Basic Navigation"

    override fun getMethods(): List<FeatureMethod> = listOf(
        MethodReturnsValue(
            "isVisible(): Boolean",
            value = { isVisible().toString() }
        ),
        MethodCallsAction(
            "setVisible(Boolean)",
            action = { setVisible(!isVisible()) }
        ),
        MethodReturnsValue(
            "getType(): NavigationType",
            value = { getType().toString() }
        ),
        MethodChangesOption(
            "setType(NavigationType)",
            options = { NavigationType.entries.map(NavigationType::name) },
            onSelectOption = { setType(NavigationType.valueOf(it)).also { setVisible(true) } },
            getSelectedOption = { typeState.value.name }
        )
    )

    override fun setType(type: NavigationType) {
        typeState.value = type
    }

    override fun isVisible(): Boolean {
        return state.visible == true
    }

    override fun getType(): NavigationType {
        return typeState.value
    }

    override fun setVisible(visible: Boolean) {
        state.setVisible(visible)
    }

    override fun setItems(items: List<NavigationItem>) {
        itemsState.value = items
    }

    @Composable
    override fun onProvideContent(context: FeatureContext, content: @Composable (() -> Unit)) {
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

    override fun onProvideNavigation(context: FeatureContext, builder: NavGraphBuilder) {

    }

    private fun map(context: FeatureContext, item: NavigationItem, onRoute: (Any) -> Unit) =
        DsNavigationItem(
            label = item.label,
            enabled = item.enabled,
            showLabel = item.showLabel,
            activeIcon = item.activeIcon,
            inactiveIcon = item.inactiveIcon,
            onClick = { onRoute(item.route) },
            id = context.getBackStackId(item.route)
        )

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