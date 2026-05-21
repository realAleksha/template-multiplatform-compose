package feature.common.api.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import feature.common.api.BaseFeatureProvider
import feature.common.api.FeatureContext
import feature.common.api.preview.presentation.FeaturePreviewIcons
import feature.common.api.preview.presentation.FeaturePreviewRoute
import feature.common.api.preview.presentation.FeaturePreviewScreen
import kotlinx.coroutines.flow.map
import shared.presentation.ui.component.DsSmallFloatingActionButton

object FeaturePreviewProvider : BaseFeatureProvider() {

    @Composable
    override fun onProvideContent(context: FeatureContext, content: @Composable () -> Unit) {
        content()

        val previewVisible = remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            val id = context.getDestinationId(FeaturePreviewRoute)
            context.getCurrentBackStackChanges()
                .map { ids -> ids.contains(id) }
                .collect(previewVisible::value::set)
        }

        if (!previewVisible.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd,
            ) {
                DsSmallFloatingActionButton(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    modifier = Modifier
                        .padding(8.dp)
                        .statusBarsPadding()
                        .alpha(0.8f),
                    icon = FeaturePreviewIcons.debugPanel(false),
                    onClick = { context.pushDestination(FeaturePreviewRoute) }
                )
            }
        }
    }

    override fun onProvideNavigation(context: FeatureContext, builder: NavGraphBuilder) {
        builder.composable<FeaturePreviewRoute> {
            FeaturePreviewScreen(
                title = "Features",
                onBack = context::popDestination,
                modifier = Modifier.fillMaxSize(),
                previews = context.features.filterIsInstance<FeaturePreview>(),
            )
        }
    }
}
