package feature.common.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder

abstract class BaseFeatureProvider : FeatureProvider {

    @Composable
    override fun provideContent(context: FeatureContext, content: @Composable (() -> Unit)) {
        onProvideContent(context, content)
    }

    @Composable
    abstract fun onProvideContent(context: FeatureContext, content: @Composable () -> Unit)

    override fun provideNavigation(context: FeatureContext, builder: NavGraphBuilder) {
        onProvideNavigation(context, builder)
    }

    open fun onProvideNavigation(context: FeatureContext, builder: NavGraphBuilder) = Unit
}