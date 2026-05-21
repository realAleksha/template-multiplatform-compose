package feature.navigation.basic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.Snapshot
import feature.common.api.Feature
import feature.common.api.FeatureContext
import feature.common.koin.KoinFeatureProvider
import feature.common.api.preview.FeatureMethod
import feature.common.api.preview.FeaturePreview
import feature.common.api.preview.MethodCallsAction
import feature.common.api.preview.MethodChangesOption
import feature.common.api.preview.MethodReturnsValue
import feature.navigation.api.NavigationFeature
import feature.navigation.api.NavigationItem
import feature.navigation.api.NavigationType
import feature.navigation.basic.presentation.NavigationProvider
import feature.navigation.basic.presentation.NavigationState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import shared.presentation.ui.container.DsNavigationItem
import kotlin.reflect.KClass

class BasicNavigationProvider(
    type: NavigationType = NavigationType.Adaptive,
    items: List<NavigationItem>? = null
) : KoinFeatureProvider(), FeaturePreview, NavigationFeature {

    private val itemsState by lazy { mutableStateOf(items) }
    private val typeState by lazy { mutableStateOf(type) }
    private val state by lazy { NavigationMutableState() }

    override val name: String = "Basic Navigation"

    override val type: KClass<out Feature> = NavigationFeature::class

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

    override fun setItems(vararg items: NavigationItem) {
        itemsState.value = items.toList()
    }

    @Composable
    override fun onProvideContent(context: FeatureContext, content: @Composable (() -> Unit)) {
        LaunchedEffect(context) {
            snapshotFlow { itemsState.value }
                .filterNotNull()
                .map { items -> items.map { item -> map(context, item, context::setDestination) } }
                .collect { items ->
                    val itemsById = items.associateBy(DsNavigationItem::id)
                    val selected = context.getCurrentDestination()?.let(itemsById::get)
                    Snapshot.withMutableSnapshot {
                        state.setNavigationItems(items)
                        state.setSelectedItem(selected)
                        state.setVisible(items.isNotEmpty())
                        state.ready = true
                    }
                    context.getCurrentDestinationChanges()
                        .map(itemsById::get)
                        .collectLatest(state::setSelectedItem)
                }
        }
        if (state.ready) {
            NavigationProvider(
                type = typeState.value,
                content = content,
                state = state
            )
        }
    }

    private fun map(context: FeatureContext, item: NavigationItem, onRoute: (Any) -> Unit) =
        DsNavigationItem(
            label = item.label,
            enabled = item.enabled,
            showLabel = item.showLabel,
            activeIcon = item.activeIcon,
            inactiveIcon = item.inactiveIcon,
            onClick = { onRoute(item.route) },
            id = context.getDestinationId(item.route)
        )

    private inner class NavigationMutableState() : NavigationState {

        override var items: List<DsNavigationItem> by mutableStateOf(emptyList())
        override var selected: DsNavigationItem? by mutableStateOf(null)
        override var visible: Boolean? by mutableStateOf(null)
        var ready: Boolean by mutableStateOf(false)

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