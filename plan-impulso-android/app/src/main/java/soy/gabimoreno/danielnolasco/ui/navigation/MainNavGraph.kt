package soy.gabimoreno.danielnolasco.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavType

object CatListNavDestination : NavDestination(CAT_LIST_ROUTE) {

    fun navigateToCatDetail(navController: NavController, name: String) {
        navController.navigate("${CatDetailNavDestination.rawRoute}/$name")
    }
}

object CatDetailNavDestination : NavDeepLinkDestination(
    rawRoute = CAT_DETAIL_ROUTE,
    rawArgs = listOf(NavArg(CAT_DETAIL_NAME_ARG, NavType.StringType)),
    rawDeepLinks = listOf(CAT_DETAIL_DEEPLINK_ROUTE)
)

object DogListNavDestination : NavDestination(DOG_LIST_ROUTE) {

    fun navigateToDogDetail(navController: NavController, name: String) {
        navController.navigate("${DogDetailNavDestination.rawRoute}/$name")
    }
}

object DogDetailNavDestination : NavDeepLinkDestination(
    rawRoute = DOG_DETAIL_ROUTE,
    rawArgs = listOf(NavArg(DOG_DETAIL_NAME_ARG, NavType.StringType)),
    rawDeepLinks = listOf(DOG_DETAIL_DEEPLINK_ROUTE)
)

object WalkingHomeNavDestination : NavDestination(WALKING_HOME_ROUTE) {

    fun navigateToWalkingPlayground(navController: NavController) {
        navController.navigate(WalkingPlaygroundNavDestination.rawRoute)
    }

    fun navigateToWalkingHistory(navController: NavController) {
        navController.navigate(WalkingHistoryNavDestination.rawRoute)
    }
}

object WalkingPlaygroundNavDestination : NavDestination(WALKING_PLAYGROUND_ROUTE)

object WalkingHistoryNavDestination : NavDestination(WALKING_HISTORY_ROUTE) {

    fun navigateToWalkingSessionDetail(navController: NavController, startTime: Long) {
        navController.navigate("${WalkingSessionDetailNavDestination.rawRoute}/$startTime")
    }
}

object WalkingSessionDetailNavDestination : NavArgsDestination(
    rawRoute = WALKING_SESSION_DETAIL_ROUTE,
    rawArgs = listOf(NavArg("startTime", NavType.LongType)),
)

object ProjectsNavDestination : NavDestination(PROJECTS_ROUTE) {

    fun navigateToProjectDetail(navController: NavController, id: Int) {
        navController.navigate("${ProjectDetailNavDestination.rawRoute}/$id")
    }
}

object ProjectDetailNavDestination : NavArgsDestination(
    rawRoute = PROJECT_DETAIL_ROUTE,
    rawArgs = listOf(
        NavArg(PROJECT_DETAIL_ARG_ID, NavType.IntType)
    )
) {

    fun navigateToEditProject(navController: NavController, id: Int) {
        navController.navigate("${EditProjectNavDestination.rawRoute}/$id")
    }
}

object EditProjectNavDestination : NavArgsDestination(
    rawRoute = EDIT_PROJECT_ROUTE,
    rawArgs = listOf(
        NavArg(EDIT_PROJECT_ARG_ID, NavType.IntType)
    )
)

private const val DEEP_LINK_ROOT = "app://soy.gabimoreno.danielnolasco"

private const val CAT_LIST_ROUTE = "cat"
private const val CAT_DETAIL_ROUTE = "cat/detail"
private const val CAT_DETAIL_NAME_ARG = "name"
const val CAT_DETAIL_DEEPLINK_ROUTE = "$DEEP_LINK_ROOT/$CAT_DETAIL_ROUTE"

private const val DOG_LIST_ROUTE = "dog"
private const val DOG_DETAIL_ROUTE = "dog/detail"
private const val DOG_DETAIL_NAME_ARG = "name"
const val DOG_DETAIL_DEEPLINK_ROUTE = "$DEEP_LINK_ROOT/$DOG_DETAIL_ROUTE"

private const val WALKING_HOME_ROUTE = "walking"
private const val WALKING_PLAYGROUND_ROUTE = "walking/playground"
private const val WALKING_HISTORY_ROUTE = "walking/history"
private const val WALKING_SESSION_DETAIL_ROUTE = "walking/history"

private const val PROJECTS_ROUTE = "projects"
private const val PROJECT_DETAIL_ROUTE = "projects/detail"
const val PROJECT_DETAIL_ARG_ID = "id"
private const val EDIT_PROJECT_ROUTE = "projects/edit"
const val EDIT_PROJECT_ARG_ID = "id"
