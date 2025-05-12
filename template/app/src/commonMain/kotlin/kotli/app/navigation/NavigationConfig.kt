package kotli.app.navigation

import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.initializer
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotli.app.navigation.a.presentation.ARoute
import kotli.app.navigation.a.presentation.AScreen
import kotli.app.navigation.a.presentation.AViewModel
import kotli.app.navigation.b.presentation.BRoute
import kotli.app.navigation.b.presentation.BScreen
import kotli.app.navigation.b.presentation.BViewModel
import kotli.app.navigation.c.presentation.CRoute
import kotli.app.navigation.c.presentation.CScreen
import kotli.app.navigation.c.presentation.CViewModel
import org.koin.dsl.module

fun NavGraphBuilder.navigation(navController: NavHostController) {
    composable<ARoute> { AScreen() }
    composable<BRoute> { BScreen() }
    composable<CRoute> { CScreen() }
}

fun InitializerViewModelFactoryBuilder.navigation() {
    initializer { AViewModel() }
    initializer { BViewModel() }
    initializer { CViewModel() }
}

val navigation = module {}