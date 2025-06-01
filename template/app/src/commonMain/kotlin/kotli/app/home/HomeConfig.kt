package kotli.app.home

import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.initializer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotli.app.get
import kotli.app.home.presentation.HomeRoute
import kotli.app.home.presentation.HomeScreen
import kotli.app.home.presentation.HomeViewModel
import org.koin.dsl.module

fun NavGraphBuilder.home(navController: NavHostController) {
    composable<HomeRoute> { HomeScreen() }
}

fun InitializerViewModelFactoryBuilder.home() {
    initializer { HomeViewModel(get()) }
}

val home = module {}