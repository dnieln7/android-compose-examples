package soy.gabimoreno.danielnolasco.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import soy.gabimoreno.danielnolasco.ui.screen.catdetail.CatDetailScreen
import soy.gabimoreno.danielnolasco.ui.screen.catlist.CatListScreen
import soy.gabimoreno.danielnolasco.ui.screen.dogdetail.DogDetailScreen
import soy.gabimoreno.danielnolasco.ui.screen.doglist.DogListScreen
import soy.gabimoreno.danielnolasco.ui.screen.walkinghistory.WalkingHistoryScreen
import soy.gabimoreno.danielnolasco.ui.screen.walkinghome.WalkingHomeScreen
import soy.gabimoreno.danielnolasco.ui.screen.walkingplayground.WalkingPlaygroundScreen
import soy.gabimoreno.danielnolasco.ui.screen.walkingsessiondetail.WalkingSessionDetailScreen
import xyz.dnieln7.portfolio.ui.screen.editproject.EditProjectScreen
import xyz.dnieln7.portfolio.ui.screen.projectdetail.ProjectDetailScreen
import xyz.dnieln7.portfolio.ui.screen.projects.ProjectsScreen

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = CatListNavDestination.rawRoute
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = CatListNavDestination.route) {
            CatListScreen(
                navigateToCatDetail = {
                    CatListNavDestination.navigateToCatDetail(
                        navController = navController,
                        name = it
                    )
                }
            )
        }
        composable(
            route = CatDetailNavDestination.route,
            arguments = CatDetailNavDestination.args,
            deepLinks = CatDetailNavDestination.deepLinks,
        ) {
            CatDetailScreen(
                name = it.arguments?.getString(CatDetailNavDestination.rawArgs[0].key) ?: "",
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = DogListNavDestination.route) {
            DogListScreen(
                navigateDogToDetail = {
                    DogListNavDestination.navigateToDogDetail(
                        navController = navController,
                        name = it
                    )
                }
            )
        }
        composable(
            route = DogDetailNavDestination.route,
            arguments = DogDetailNavDestination.args,
            deepLinks = DogDetailNavDestination.deepLinks,
        ) {
            DogDetailScreen(
                name = it.arguments
                    ?.getString(DogDetailNavDestination.rawArgs[0].key)
                    ?: DEFAULT_STRING,
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = WalkingHomeNavDestination.route) {
            WalkingHomeScreen(
                navigateToWalkingPlayground = {
                    WalkingHomeNavDestination.navigateToWalkingPlayground(navController)
                },
                navigateToWalkingHistory = {
                    WalkingHomeNavDestination.navigateToWalkingHistory(navController)
                }
            )
        }
        composable(
            route = WalkingPlaygroundNavDestination.route,
        ) {
            WalkingPlaygroundScreen(
                navigateBack = { navController.navigateUp() },
            )
        }
        composable(
            route = WalkingHistoryNavDestination.route,
        ) {
            WalkingHistoryScreen(
                navigateToWalkingSessionDetail = {
                    WalkingHistoryNavDestination.navigateToWalkingSessionDetail(navController, it)
                },
                navigateBack = { navController.navigateUp() },
            )
        }
        composable(
            route = WalkingSessionDetailNavDestination.route,
            arguments = WalkingSessionDetailNavDestination.args,
        ) {
            WalkingSessionDetailScreen(
                startTime = it.arguments
                    ?.getLong(WalkingSessionDetailNavDestination.rawArgs[0].key)
                    ?: DEFAULT_LONG,
                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = ProjectsNavDestination.route) {
            ProjectsScreen(
                navigateToProjectDetail = {
                    ProjectsNavDestination.navigateToProjectDetail(navController, it)
                }
            )
        }
        composable(
            route = ProjectDetailNavDestination.route,
            arguments = ProjectDetailNavDestination.args,
        ) {
            ProjectDetailScreen(
                id = it.arguments?.getInt(PROJECT_DETAIL_ARG_ID) ?: DEFAULT_INT,
                navigateBack = { navController.navigateUp() },
                navigateToEdit = { id ->
                    ProjectDetailNavDestination.navigateToEditProject(navController, id)
                }
            )
        }
        composable(
            route = EditProjectNavDestination.route,
            arguments = EditProjectNavDestination.args,
        ) {
            EditProjectScreen(
                id = it.arguments?.getInt(EDIT_PROJECT_ARG_ID) ?: DEFAULT_INT,
                navigateBack = { navController.navigateUp() },
            )
        }
    }
}

private const val DEFAULT_STRING = ""
private const val DEFAULT_INT = -1
private const val DEFAULT_LONG = -1L
