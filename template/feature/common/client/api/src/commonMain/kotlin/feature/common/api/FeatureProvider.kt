package feature.common.api

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder

interface FeatureProvider : Feature {

    @Composable
    fun provideContent(context: FeatureContext, content: @Composable (() -> Unit))

    fun provideNavigation(context: FeatureContext, builder: NavGraphBuilder)
}