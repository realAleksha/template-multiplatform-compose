package feature.common.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder

abstract class BaseFeatureProvider : FeatureProvider {

    @Composable
    override fun provideContent(context: FeatureNavContext, content: @Composable (() -> Unit)) {
        onProvideContent(context, content)
    }

    @Composable
    abstract fun onProvideContent(context: FeatureNavContext, content: @Composable () -> Unit)

    override fun provideNavigation(context: FeatureNavContext, builder: NavGraphBuilder) {
        onProvideNavigation(context, builder)
    }

    open fun onProvideNavigation(context: FeatureNavContext, builder: NavGraphBuilder) = Unit
}