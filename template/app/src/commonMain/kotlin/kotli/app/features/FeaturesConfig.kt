package kotli.app.features

import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.initializer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotli.app.features.presentation.FeaturesRoute
import kotli.app.features.presentation.FeaturesScreen
import kotli.app.features.presentation.FeaturesViewModel
import kotli.app.get
import org.koin.dsl.module

fun NavGraphBuilder.features(navController: NavHostController) {
    composable<FeaturesRoute> { FeaturesScreen() }
}

fun InitializerViewModelFactoryBuilder.features() {
    initializer { FeaturesViewModel(get()) }
}

val features = module {}