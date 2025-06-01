package shared.presentation.ui.container

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import shared.presentation.ui.component.DsIcon
import shared.presentation.ui.component.DsSpacer8
import shared.presentation.ui.component.DsText
import shared.presentation.ui.icon.DsIconModel

data class DsNavigationItem(
    val id: Int,
    val label: String?,
    val enabled: Boolean,
    val showLabel: Boolean,
    val activeIcon: DsIconModel,
    val inactiveIcon: DsIconModel,
    val onClick: () -> Unit
) {
    @Stable
    fun getIcon(selected: Boolean): DsIconModel = if (selected) activeIcon else inactiveIcon
}

@Stable
interface DsNavigationState {

    val items: List<DsNavigationItem>
    val selected: DsNavigationItem?
    val visible: Boolean?

    fun setVisible(visible: Boolean)
    fun setSelectedItem(selected: DsNavigationItem?)
    fun setNavigationItems(items: List<DsNavigationItem>)
}

@Composable
fun DsBottomNavigation(
    state: DsNavigationState,
    modifier: Modifier = Modifier,
) {
    if (state.visible == false) return
    val items = state.items.takeIf { items -> items.isNotEmpty() } ?: return

    NavigationBar(modifier) {
        val selected = state.selected
        items.forEach { item ->
            val isSelected = item.id == selected?.id
            NavigationBarItem(
                label = {
                    DsText(
                        textAlign = TextAlign.Center,
                        text = item.label
                    )
                },
                icon = { DsIcon(model = item.getIcon(isSelected)) },
                alwaysShowLabel = item.showLabel,
                onClick = item.onClick,
                selected = isSelected
            )
        }
    }
}

@Composable
fun DsDismissibleNavigation(
    state: DsNavigationState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val items = state.items.takeIf { items -> items.isNotEmpty() } ?: return run { content() }

    DismissibleNavigationDrawer(
        modifier = modifier,
        drawerState = createDrawerState(state),
        drawerContent = {
            DismissibleDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                DsSpacer8()
                val selected = state.selected
                items.forEach { item ->
                    val isSelected = item.id == selected?.id
                    NavigationDrawerItem(
                        label = { DsText(text = item.label) },
                        icon = { DsIcon(model = item.getIcon(isSelected)) },
                        selected = isSelected,
                        onClick = {
                            item.onClick()
                            state.setVisible(false)
                        }
                    )
                }
                DsSpacer8()
            }
        },
        content = content
    )
}

@Composable
fun DsModalNavigation(
    state: DsNavigationState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val items = state.items.takeIf { items -> items.isNotEmpty() } ?: return run { content() }

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = createDrawerState(state),
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                DsSpacer8()
                val selected = state.selected
                items.forEach { item ->
                    val isSelected = item.id == selected?.id
                    NavigationDrawerItem(
                        label = { DsText(text = item.label) },
                        icon = { DsIcon(model = item.getIcon(isSelected)) },
                        selected = isSelected,
                        onClick = {
                            item.onClick()
                            state.setVisible(false)
                        }
                    )
                }
                DsSpacer8()
            }
        },
        content = content
    )
}

@Composable
fun DsPermanentNavigation(
    state: DsNavigationState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    if (state.visible == false) return run { content() }
    val items = state.items.takeIf({ items -> items.isNotEmpty() }) ?: return run { content() }

    PermanentNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                DsSpacer8()
                val selected = state.selected
                items.forEach { item ->
                    val isSelected = item.id == selected?.id
                    NavigationDrawerItem(
                        label = { DsText(text = item.label) },
                        icon = { DsIcon(model = item.getIcon(isSelected), size = 24.dp) },
                        selected = isSelected,
                        onClick = item.onClick
                    )
                }
                DsSpacer8()
            }
        },
        content = content
    )
}

@Composable
fun DsRailNavigation(
    state: DsNavigationState,
    modifier: Modifier = Modifier
) {
    if (state.visible == false) return
    val items = state.items.takeIf { items -> items.isNotEmpty() } ?: return

    NavigationRail(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        content = {
            DsSpacer8()
            val selected = state.selected
            items.forEach { item ->
                val isSelected = item.id == selected?.id
                NavigationRailItem(
                    label = { DsText(text = item.label) },
                    icon = { DsIcon(model = item.getIcon(isSelected)) },
                    onClick = item.onClick,
                    selected = isSelected,
                )
            }
            DsSpacer8()
        }
    )
}

@Composable
private fun createDrawerState(state: DsNavigationState): DrawerState {
    val initial =
        remember(state) { if (state.visible == true) DrawerValue.Open else DrawerValue.Closed }
    val drawerState: DrawerState = rememberDrawerState(initial) { drawerValue ->
        state.setVisible(drawerValue == DrawerValue.Open)
        true
    }
    DrawerVisibilityHandler(state, drawerState)
    return drawerState
}

@Composable
private fun DrawerVisibilityHandler(
    state: DsNavigationState,
    drawerState: DrawerState
) {
    val visible = state.visible
    LaunchedEffect(visible) {
        if (!drawerState.isAnimationRunning) {
            when {
                visible == true -> drawerState.open()
                else -> drawerState.close()
            }
        }
    }
}