package shared.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.navOptions

/**
 * Navigates back in the navigation stack if there is a previous back stack entry.
 */
fun NavController.back() {
    if (previousBackStackEntry != null) {
        popBackStack()
    }
}

/**
 * Navigates to a new instance of the specified route.
 *
 * @param route The route to navigate to.
 */
fun NavController.newInstance(route: Any) {
    navigate(route)
}

/**
 * Navigates to a single instance of the specified route.
 * If the route already exists in the back stack, it will be popped up to and replaced.
 *
 * @param route The route to navigate to.
 */
fun NavController.singleInstance(route: Any) {
    navigate(route, navOptions {
        popUpTo(route) {
            inclusive = true
        }
        launchSingleTop = true
        restoreState = false
    })
}

/**
 * Replaces the previous destination with the specified route.
 *
 * @param route The route to navigate to.
 */
fun NavController.replacePrevious(route: Any) {
    val prev = currentDestination?.route ?: route
    navigate(route, navOptions {
        if (prev is String) {
            popUpTo(prev) {
                inclusive = true
            }
        } else {
            popUpTo(prev) {
                inclusive = true
            }
        }
        launchSingleTop = true
        restoreState = false
    })
}

/**
 * Clears the navigation history and navigates to the specified route.
 *
 * @param route The route to navigate to.
 */
fun NavController.clearHistory(route: Any) {
    navigate(route, navOptions {
        graph.startDestinationRoute?.let { graphRoute ->
            popUpTo(graphRoute) {
                inclusive = false
            }
        }
    })
}
