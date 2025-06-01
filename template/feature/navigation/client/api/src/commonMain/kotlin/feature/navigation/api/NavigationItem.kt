package feature.navigation.api

import shared.presentation.ui.icon.DsIconModel

data class NavigationItem(
    val route: Any,
    val activeIcon: DsIconModel,
    val inactiveIcon: DsIconModel = activeIcon,
    val showLabel: Boolean = true,
    val enabled: Boolean = true,
    val label: String? = null,
)