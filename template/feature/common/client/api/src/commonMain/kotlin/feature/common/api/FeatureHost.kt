package feature.common.api

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.serialization.generateHashCode
import feature.common.api.preview.FeaturePreviewScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.serializer
import shared.presentation.navigation.popDestination
import shared.presentation.navigation.setDestination
import shared.presentation.navigation.pushDestination
import shared.presentation.navigation.replaceDestination
import shared.presentation.navigation.restoreDestination
import shared.presentation.ui.container.DsScaffold

private val nextFeatureIndex = staticCompositionLocalOf { 0 }

@Composable
fun FeatureHost(
    context: FeatureHostContext,
    startDestinationProvider: () -> Any?,
    navGraphBuilder: NavGraphBuilder.(navController: NavHostController) -> Unit = {}
) {
    val index = nextFeatureIndex.current
    val feature = remember(index) { context.features.getOrNull(index) as? FeatureProvider }
    if (feature != null) {
        CompositionLocalProvider(nextFeatureIndex provides index + 1) {
            feature.provideContent(context) {
                FeatureHost(context, startDestinationProvider, navGraphBuilder)
            }
        }
    } else {
        Content(context, startDestinationProvider, navGraphBuilder)
    }
}

@Composable
private fun Content(
    context: FeatureHostContext,
    startDestinationProvider: () -> Any?,
    navGraphBuilder: NavGraphBuilder.(navController: NavHostController) -> Unit = {}
) {
    startDestinationProvider()?.let { startDestination ->
        DsScaffold { paddings ->
            NavHost(
                modifier = Modifier.fillMaxSize().padding(paddings),
                startDestination = startDestination,
                contentAlignment = Alignment.Center,
                navController = context.navController,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                builder = {
                    val navBuilder = this
                    context.features.forEach { feature ->
                        feature as FeatureProvider
                        feature.provideNavigation(context, navBuilder)
                    }
                    navGraphBuilder(context.navController)
                }
            )
            FeaturePreviewScreen(context)
        }
    }
}

data class FeatureHostContext(
    internal val debug: Boolean,
    internal val features: List<Feature>,
    internal val navController: NavHostController
) : FeatureContext {

    override fun getCurrentDestinationChanges(): Flow<Int> = navController.currentBackStackEntryFlow
        .map { entry -> entry.destination.id }
        .distinctUntilChanged()

    override fun getCurrentDestination(): Int? = navController.currentBackStackEntry?.destination?.id

    override fun getDestinationId(route: Any): Int = route::class.serializer().generateHashCode()

    override fun replaceDestination(route: Any) = navController.replaceDestination(route)

    override fun restoreDestination(route: Any) = navController.restoreDestination(route)

    override fun setDestination(route: Any) = navController.setDestination(route)

    override fun pushDestination(route: Any) = navController.pushDestination(route)

    override fun popDestination() = navController.popDestination()
}