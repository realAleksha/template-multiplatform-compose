package kotli.app.presentation

import feature.navigation.api.NavigationFeature
import feature.navigation.api.NavigationItem
import feature.splash.api.SplashFeature
import kotli.common.presentation.Icons
import kotli.home.presentation.HomeRoute
import shared.presentation.viewmodel.BaseViewModel

class AppViewModel(
    val state: AppState
) : BaseViewModel() {

    override fun doBind() {
        withState {
            state.setStartDestination(HomeRoute)
            // {feature.navigation.client.api}
            state.context.get(NavigationFeature::class).setItems(
                NavigationItem(
                    label = "Home",
                    route = HomeRoute,
                    activeIcon = Icons.home,
                )
            )
            // {feature.navigation.client.api}
            state.context.get(SplashFeature::class).setVisible(false)
        }
    }
}