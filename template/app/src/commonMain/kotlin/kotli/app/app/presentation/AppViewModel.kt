package kotli.app.app.presentation

import feature.navigation.api.NavigationFeature
import feature.navigation.api.NavigationItem
import feature.splash.api.SplashFeature
import kotli.app.feature.presentation.FeatureRoute
import kotli.app.home.presentation.HomeRoute
import shared.presentation.ui.icon.DsIcons
import shared.presentation.viewmodel.BaseViewModel

class AppViewModel(
    private val navigationFeature: NavigationFeature,
    private val splashFeature: SplashFeature,
    private val _state: AppMutableState
) : BaseViewModel() {

    val state: AppState = _state

    override fun doBind() {
        withState {
            navigationFeature.setItems(
                NavigationItem(label = "Home", route = HomeRoute, activeIcon = DsIcons.home),
                NavigationItem(label = "Feature", route = FeatureRoute("Feature"), activeIcon = DsIcons.school)
            )
            _state.startDestination = HomeRoute
            splashFeature.setVisible(false)
        }
    }
}