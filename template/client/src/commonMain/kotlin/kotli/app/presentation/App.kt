package kotli.app.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import feature.common.api.FeatureNavHost
import feature.common.api.FeatureNavHostContext
import kotli.app.app
import kotli.getViewModel

@Composable
fun App() {
    val viewModel: AppViewModel = getViewModel()
    val state = viewModel.state

    val context = FeatureNavHostContext(
        navController = rememberNavController(),
        context = state.context,
        debug = true
    )

    FeatureNavHost(
        context = context,
        navGraphBuilder = { app(it) },
        startDestinationProvider = state::start::get,
    )
}
