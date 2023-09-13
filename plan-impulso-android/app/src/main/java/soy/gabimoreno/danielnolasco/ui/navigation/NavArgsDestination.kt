package soy.gabimoreno.danielnolasco.ui.navigation

import androidx.navigation.navArgument

abstract class NavArgsDestination(
    rawRoute: String,
    val rawArgs: List<NavArg>,
) : NavDestination(rawRoute) {
    override val route = run {
        return@run if (rawArgs.isEmpty()) {
            rawRoute
        } else {
            rawRoute.plus("/").plus(
                rawArgs.joinToString("/") { "{${it.key}}" }
            )
        }
    }

    val args = rawArgs.map { navArgument(it.key) { type = it.type } }
}
