package feature.common.api

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.serialization.generateHashCode
import feature.common.api.preview.FeaturePreviewProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.serializer
import shared.presentation.navigation.popDestination
import shared.presentation.navigation.pushDestination
import shared.presentation.navigation.replaceDestination
import shared.presentation.navigation.restoreDestination
import shared.presentation.navigation.setDestination
import shared.presentation.ui.container.DsScaffold
import kotlin.reflect.KClass

private val nextFeatureIndex = compositionLocalOf { 0 }

@Composable
fun FeatureNavHost(
    context: FeatureNavHostContext,
    startDestinationProvider: () -> Any?,
    navGraphBuilder: NavGraphBuilder.(navController: NavHostController) -> Unit = {}
) {
    val index = nextFeatureIndex.current
    val feature = remember(index) { context.features.getOrNull(index) }
    if (feature != null) {
        CompositionLocalProvider(nextFeatureIndex provides index + 1) {
            if (feature.isAvailable()) {
                feature.provideContent(context) {
                    FeatureNavHost(context, startDestinationProvider, navGraphBuilder)
                }
            } else {
                FeatureNavHost(context, startDestinationProvider, navGraphBuilder)
            }
        }
    } else {
        if (context.debug) {
            val preview = FeaturePreviewProvider
            preview.provideContent(context) {
                val newContext = remember { context.copy(features = context.features + preview) }
                Content(newContext, startDestinationProvider, navGraphBuilder)
            }
        } else {
            Content(context, startDestinationProvider, navGraphBuilder)
        }
    }
}

@Composable
private fun Content(
    context: FeatureNavHostContext,
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
                    context.features.forEach { it.provideNavigation(context, this) }
                    navGraphBuilder(context.navController)
                }
            )
        }
    }
}

data class FeatureNavHostContext(
    internal val debug: Boolean,
    internal val context: FeatureContext,
    internal val navController: NavHostController,
    override val features: List<FeatureProvider> = context.features.filterIsInstance<FeatureProvider>()
) : FeatureNavContext {

    override fun <T : Feature> get(type: KClass<T>): T = context.get(type)

    override fun <T : Feature> getOrNull(type: KClass<T>): T? = context.getOrNull(type)

    override fun getCurrentBackStackChanges(): Flow<List<Int>> = navController.currentBackStack
        .map { entries -> entries.map { entry -> entry.destination.id } }
        .distinctUntilChanged()

    override fun getCurrentDestinationChanges(): Flow<Int> = navController.currentBackStackEntryFlow
        .map { entry -> entry.destination.id }
        .distinctUntilChanged()

    override fun getCurrentDestination(): Int? =
        navController.currentBackStackEntry?.destination?.id

    override fun getDestinationId(route: Any): Int = route::class.serializer().generateHashCode()

    override fun replaceDestination(route: Any) = navController.replaceDestination(route)

    override fun restoreDestination(route: Any) = navController.restoreDestination(route)

    override fun setDestination(route: Any) = navController.setDestination(route)

    override fun pushDestination(route: Any) = navController.pushDestination(route)

    override fun popDestination() = navController.popDestination()
}