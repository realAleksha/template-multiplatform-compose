package kotli.app.feature

import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.initializer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotli.app.feature.presentation.FeatureRoute
import kotli.app.feature.presentation.FeatureScreen
import kotli.app.feature.presentation.FeatureViewModel
import org.koin.dsl.module
import shared.presentation.navigation.back

fun NavGraphBuilder.feature(navController: NavHostController) {
    composable<FeatureRoute> {
        val route = it.toRoute<FeatureRoute>()
        FeatureScreen(
            title = route.title,
            onBack = navController::back
        )
    }
}

fun InitializerViewModelFactoryBuilder.feature() {
    initializer { FeatureViewModel() }
}

val feature = module {}