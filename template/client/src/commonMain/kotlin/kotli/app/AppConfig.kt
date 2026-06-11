package kotli.app

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import feature.ads.admob.AdMobAdsProvider
import feature.ads.api.AdsFeature
import feature.analytics.api.AnalyticsFeature
import feature.analytics.firebase.FirebaseAnalyticsProvider
import feature.analytics.stub.StubAnalyticsProvider
import feature.auth.api.AuthFeature
import feature.auth.stub.StubAuthProvider
import feature.auth.supabase.SupabaseAuthProvider
import feature.common.api.BasicFeatureContext
import feature.common.api.FeatureContext
import feature.loader.api.LoaderFeature
import feature.loader.basic.BasicLoaderProvider
import feature.navigation.api.NavigationFeature
import feature.navigation.basic.BasicNavigationProvider
import feature.passcode.api.PasscodeFeature
import feature.passcode.basic.BasicPasscodeProvider
import feature.payments.api.PaymentsFeature
import feature.payments.revenuecat.RevenueCatPaymentsProvider
import feature.review.api.ReviewFeature
import feature.review.market.MarketReviewProvider
import feature.review.stub.StubReviewProvider
import feature.splash.api.SplashFeature
import feature.splash.basic.BasicSplashProvider
import feature.theme.api.ThemeFeature
import feature.theme.basic.BasicThemeProvider
import feature.update.api.UpdateFeature
import feature.update.sideload.DefaultSideloadUpdateStateResolver
import feature.update.sideload.SideloadUpdateProvider
import feature.update.store.StoreUpdateProvider
import kotli.app.presentation.AppMutableState
import kotli.app.presentation.AppState
import kotli.app.presentation.AppViewModel
import kotli.common.common
import kotli.common.data.source.supabase.SupabaseSource
import kotli.home.home
import kotli.platform.platform
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import shared.presentation.theme.DefaultThemeState
import shared.presentation.theme.ThemeConfig
import shared.presentation.theme.ThemeState
import shared.presentation.ui.theme.DsThemes

fun NavGraphBuilder.app(navController: NavHostController) {
    platform(navController)
    common(navController)
    home(navController)
}

val app = module {
    includes(
        platform,
        common,
        home
    )
    viewModelOf(::AppViewModel)
    singleOf(::AppMutableState).bind<AppState>()
    single<ThemeState> {
        DefaultThemeState(
            defaultConfig = ThemeConfig(
                defaultTheme = DsThemes.Light,
                lightTheme = DsThemes.Light,
                darkTheme = DsThemes.Dark,
            )
        )
    }
    single { BasicSplashProvider() }.bind<SplashFeature>()
    single { BasicLoaderProvider() }.bind<LoaderFeature>()
    single { BasicNavigationProvider() }.bind<NavigationFeature>()
    single { RevenueCatPaymentsProvider(apiKey = "REVENUECAT_API_KEY", apiUserId = "REVENUECAT_API_USER_ID") }.bind<PaymentsFeature>()
    single { BasicThemeProvider(get(), get()) }.bind<ThemeFeature>()
    single { BasicPasscodeProvider(get(), get()) }.bind<PasscodeFeature>()
    single { AdMobAdsProvider() }.bind<AdsFeature>()
    // {feature.review.client.stub}
    single { StubReviewProvider() }.bind<ReviewFeature>()
    // {feature.review.client.stub}
    // {feature.review.client.market}
    single { MarketReviewProvider() }.bind<ReviewFeature>()
    // {feature.review.client.market}
    // {feature.analytics.client.stub}
    single { StubAnalyticsProvider() }.bind<AnalyticsFeature>()
    // {feature.analytics.client.stub}
    // {feature.analytics.client.firebase}
    single { FirebaseAnalyticsProvider() }.bind<AnalyticsFeature>()
    // {feature.analytics.client.firebase}
    // {feature.update.client.sideload}
    single {
        SideloadUpdateProvider(
            httpSource = get(),
            settingsSource = get(),
            resolver = DefaultSideloadUpdateStateResolver(
                metadataUrl = "https://example.com/update.json",
                httpSource = get(),
                currentVersionCode = 1
            )
        )
    }.bind<UpdateFeature>()
    // {feature.update.client.sideload}
    // {feature.update.client.store}
    single { StoreUpdateProvider() }.bind<UpdateFeature>()
    // {feature.update.client.store}
    single { StubAuthProvider() }.bind<AuthFeature>()
    single { SupabaseAuthProvider(get<SupabaseSource>().client) }.bind<AuthFeature>()
    // {feature.common.client.api}
    single<FeatureContext> {
        BasicFeatureContext(
            listOf(
                get<SplashFeature>(),
                get<ThemeFeature>(),
                get<LoaderFeature>(),
                get<PasscodeFeature>(),
                get<NavigationFeature>(),
                get<StubAuthProvider>(),
                get<SupabaseAuthProvider>(),
                get<PaymentsFeature>(),
                get<SideloadUpdateProvider>(),
                get<StoreUpdateProvider>(),
                get<AdMobAdsProvider>(),
                get<StubReviewProvider>(),
                get<MarketReviewProvider>(),
                get<StubAnalyticsProvider>(),
                get<FirebaseAnalyticsProvider>()
            )
        )
    }
    // {feature.common.client.api}
}