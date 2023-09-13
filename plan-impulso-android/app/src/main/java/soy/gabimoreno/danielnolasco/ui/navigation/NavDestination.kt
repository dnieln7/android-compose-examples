package soy.gabimoreno.danielnolasco.ui.navigation

abstract class NavDestination(val rawRoute: String) {
    open val route = rawRoute
}
