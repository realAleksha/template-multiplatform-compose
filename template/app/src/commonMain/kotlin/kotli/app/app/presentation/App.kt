package kotli.app.app.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import feature.common.api.FeatureHost
import feature.common.api.FeatureHostContext
import kotli.app.app.app
import shared.presentation.viewmodel.ViewModelProvider
import shared.presentation.viewmodel.provideViewModel

@Composable
fun App() = ViewModelProvider({ app() }) {
    val viewModel: AppViewModel = provideViewModel()
    val state = viewModel.state

    val featuresContext = FeatureHostContext(
        navController = rememberNavController(),
        features = state.features,
    )
    FeatureHost(
        context = featuresContext,
        navGraphBuilder = { app(it) },
        startDestinationProvider = state::startDestination::get,
    )
}
