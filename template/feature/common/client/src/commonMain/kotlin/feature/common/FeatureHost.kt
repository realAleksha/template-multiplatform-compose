package feature.common

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
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

@Composable
fun FeatureHost(
    context: FeatureHostContext,
    modifier: Modifier = Modifier,
    startDestinationProvider: () -> Any?,
    navGraphBuilder: NavGraphBuilder.(navController: NavHostController) -> Unit = {}
) {
    val provider = remember(context) { FeatureHostProvider(context) }

    provider.provideContent(context) {
        startDestinationProvider()?.let { startDestination ->
            NavHost(
                modifier = modifier,
                startDestination = startDestination,
                contentAlignment = Alignment.Center,
                navController = context.navController,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                builder = {
                    provider.provideNavigation(context, this)
                    navGraphBuilder(context.navController)
                }
            )
        }
    }
}

@Suppress("UNCHECKED_CAST")
data class FeatureHostContext(
    internal val features: List<FeatureProvider>,
    internal val navController: NavHostController
) : FeatureContext {

    override fun <F : Feature> get(): F = features.firstNotNullOf { it as? F }

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

@Stable
private data class FeatureHostProvider(
    private val hostContext: FeatureHostContext
) : FeatureProvider {

    private val features = hostContext.features
    private var preparedFeatureIndex: Int = 0

    @Composable
    override fun provideContent(context: FeatureContext, content: @Composable () -> Unit) {
        val provider = features.getOrNull(preparedFeatureIndex++)
        provider?.provideContent(context) {
            println("recompose $provider")
            provideContent(context, content)
        } ?: content()
    }

    override fun provideNavigation(context: FeatureContext, builder: NavGraphBuilder) {
        features.forEach { feature -> feature.provideNavigation(context, builder) }
    }
}