package feature.common.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder

interface FeatureProvider : Feature {

    @Composable
    fun provideContent(context: FeatureNavContext, content: @Composable (() -> Unit))

    fun provideNavigation(context: FeatureNavContext, builder: NavGraphBuilder)
}