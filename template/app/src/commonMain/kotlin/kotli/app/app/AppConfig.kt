package kotli.app.app

import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.initializer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import feature.auth.stub.StubAuthProvider
import feature.common.FeatureProvider
import feature.navigation.api.NavigationItem
import feature.navigation.basic.BasicNavigationProvider
import feature.passcode.basic.BasicPasscodeProvider
import feature.theme.basic.BasicThemeProvider
import kotli.app.app.presentation.AppMutableState
import kotli.app.app.presentation.AppState
import kotli.app.app.presentation.AppViewModel
import kotli.app.auth.auth
import kotli.app.auth.signin.google.presentation.flow.StubFlowProvider
import kotli.app.common.common
import kotli.app.get
import kotli.app.home.home
import kotli.app.navigation.a.presentation.ARoute
import kotli.app.navigation.b.presentation.BRoute
import kotli.app.navigation.c.presentation.CRoute
import kotli.app.navigation.navigation
import kotli.app.passcode.passcode
import kotli.app.platform.platform
import kotli.app.profile.profile
import kotli.app.showcases.presentation.ShowcasesRoute
import kotli.app.showcases.showcases
import kotli.app.template.feature.template
import kotli.app.theme.theme
import org.koin.dsl.bind
import org.koin.dsl.module
import shared.presentation.ui.component.DsSnackbarState
import shared.presentation.ui.icon.DsIcons

fun NavGraphBuilder.app(navController: NavHostController) {
    platform(navController)
    auth(navController)
    common(navController)
    home(navController)
    navigation(navController)
    passcode(navController)
    profile(navController)
    showcases(navController)
    template(navController)
    theme(navController)
}

fun InitializerViewModelFactoryBuilder.app() {
    initializer { AppViewModel(get()) }
    platform()
    auth()
    common()
    home()
    navigation()
    passcode()
    profile()
    showcases()
    template()
    theme()
}

val app = module {
    single { DsSnackbarState() }
    single {
        AppMutableState(
            snackbarState = get(),
            features = listOf<FeatureProvider>(
                BasicThemeProvider(get()),
                BasicPasscodeProvider(get(), get()),
                BasicNavigationProvider(
                    items = listOf(
                        NavigationItem(
                            route = ShowcasesRoute,
                            activeIcon = DsIcons.school
                        ),
                        NavigationItem(
                            route = ARoute,
                            activeIcon = DsIcons.wineBar
                        ),
                        NavigationItem(
                            route = BRoute,
                            activeIcon = DsIcons.localDrink
                        ),
                        NavigationItem(
                            route = CRoute,
                            activeIcon = DsIcons.coffee
                        )
                    )
                ),
                StubAuthProvider(),
            )
        )
    }.bind<AppState>()
    includes(
        platform,
        auth,
        common,
        home,
        navigation,
        passcode,
        profile,
        showcases,
        template,
        theme,
    )
}