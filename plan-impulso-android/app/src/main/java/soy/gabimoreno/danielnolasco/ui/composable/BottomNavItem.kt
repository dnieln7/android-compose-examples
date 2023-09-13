package soy.gabimoreno.danielnolasco.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import soy.gabimoreno.danielnolasco.ui.navigation.NavDestination as GraphNavDestination

@Composable
fun RowScope.BottomNavItem(
    @DrawableRes iconRes: Int,
    @StringRes labelRes: Int,
    navController: NavController,
    currentDestination: NavDestination?,
    destination: GraphNavDestination,
) {
    BottomNavigationItem(
        icon = { Icon(painterResource(iconRes), contentDescription = null) },
        label = { Text(stringResource(labelRes)) },
        selected = currentDestination
            ?.route
            ?.split("/")
            ?.firstOrNull()
            ?.equals(destination.rawRoute)
            ?: false,
        onClick = {
            navController.navigate(destination.rawRoute) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}
