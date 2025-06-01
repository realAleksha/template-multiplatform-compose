package kotli.app.app.presentation

import feature.navigation.api.NavigationFeature
import feature.navigation.api.NavigationItem
import kotli.app.features.presentation.FeaturesRoute
import kotli.app.home.presentation.HomeRoute
import shared.presentation.ui.icon.DsIcons
import shared.presentation.viewmodel.BaseViewModel

class AppViewModel(
    private val navigationFeature: NavigationFeature,
    private val _state: AppMutableState
) : BaseViewModel() {

    val state: AppState = _state

    override fun doBind() {
        withState {
            navigationFeature.setItems(
                listOf(
                    NavigationItem(
                        label = "Home",
                        route = HomeRoute,
                        activeIcon = DsIcons.home
                    ),
                    NavigationItem(
                        label = "Features",
                        route = FeaturesRoute,
                        activeIcon = DsIcons.school
                    ),
                )
            )
            _state.startDestination = HomeRoute
        }
    }
}