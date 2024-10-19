package kotli.app

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotli.app.feature.navigation.a.presentation.ARoute
import kotli.app.feature.navigation.a.presentation.AScreen
import kotli.app.feature.navigation.b.presentation.BRoute
import kotli.app.feature.navigation.b.presentation.BScreen
import kotli.app.feature.navigation.c.presentation.CRoute
import kotli.app.feature.navigation.c.presentation.CScreen
import kotli.app.feature.navigation.navigation
import kotli.app.feature.passcode.passcode
import kotli.app.feature.showcases.showcases
import kotli.app.feature.theme.theme

interface AppRoute

fun NavGraphBuilder.app(navController: NavHostController) {
    theme(navController)
    passcode(navController)
    showcases(navController)
    navigation(navController)
}