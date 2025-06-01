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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.serializer
import shared.presentation.navigation.back
import shared.presentation.navigation.clearHistory
import shared.presentation.navigation.replacePrevious
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
        }
    }
}

@Suppress("UNCHECKED_CAST")
data class FeatureHostContext(
    internal val features: List<Feature>,
    internal val navController: NavHostController
) : FeatureContext {

    override fun getCurrentBackStackIdChanges(): Flow<Int> = navController.currentBackStackEntryFlow
        .mapNotNull { entry -> entry.destination.id }
        .distinctUntilChanged()

    override fun getCurrentBackStackId(): Int? = navController.currentBackStackEntry?.destination?.id

    override fun getBackStackId(route: Any): Int = route::class.serializer().generateHashCode()

    override fun replaceBackStack(route: Any) = navController.replacePrevious(route)

    override fun setBackStack(route: Any) = navController.clearHistory(route)

    override fun addBackStack(route: Any) = navController.navigate(route)

    override fun popBackStack() = navController.back()
}