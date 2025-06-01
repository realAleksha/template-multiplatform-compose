package kotli.app.app

import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.initializer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import feature.auth.api.AuthFeature
import feature.auth.stub.StubAuthProvider
import feature.common.api.Feature
import feature.navigation.api.NavigationFeature
import feature.navigation.basic.BasicNavigationProvider
import feature.passcode.api.PasscodeFeature
import feature.passcode.basic.BasicPasscodeProvider
import feature.theme.api.ThemeFeature
import feature.theme.basic.BasicThemeProvider
import kotli.app.app.presentation.AppMutableState
import kotli.app.app.presentation.AppState
import kotli.app.app.presentation.AppViewModel
import kotli.app.common.common
import kotli.app.features.features
import kotli.app.get
import kotli.app.home.home
import kotli.app.platform.platform
import kotli.app.template.feature.template
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun NavGraphBuilder.app(navController: NavHostController) {
    platform(navController)
    common(navController)
    features(navController)
    home(navController)
    template(navController)
}

fun InitializerViewModelFactoryBuilder.app() {
    initializer { AppViewModel(get(), get()) }
    platform()
    common()
    features()
    home()
    template()
}

val app = module {
    includes(
        platform,
        common,
        features,
        home,
        template
    )

    single<ThemeFeature> { BasicThemeProvider(get()) }
    single<PasscodeFeature> { BasicPasscodeProvider(get(), get()) }
    single<NavigationFeature> { BasicNavigationProvider() }
    single<AuthFeature> { StubAuthProvider() }
//    single<AuthFeature> { SupabaseAuthProvider(get<SupabaseSource>().client) }

    single<List<Feature>> {
        listOf(
            get<ThemeFeature>(),
            get<PasscodeFeature>(),
            get<NavigationFeature>(),
            get<AuthFeature>()
        )
    }

    singleOf(::AppMutableState).bind<AppState>()
}