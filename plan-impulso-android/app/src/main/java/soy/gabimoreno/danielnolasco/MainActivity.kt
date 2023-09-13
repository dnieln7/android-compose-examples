package soy.gabimoreno.danielnolasco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import soy.gabimoreno.danielnolasco.framework.connectivity.ConnectivityMonitor
import soy.gabimoreno.danielnolasco.framework.extensions.toastShort
import soy.gabimoreno.danielnolasco.framework.permissions.checkPermissions
import soy.gabimoreno.danielnolasco.framework.permissions.permissionsLauncher
import soy.gabimoreno.danielnolasco.framework.permissions.postNotificationsPermissions
import soy.gabimoreno.danielnolasco.ui.composable.BottomNavItem
import soy.gabimoreno.danielnolasco.ui.composable.ConnectivityBanner
import soy.gabimoreno.danielnolasco.ui.navigation.CatListNavDestination
import soy.gabimoreno.danielnolasco.ui.navigation.DogListNavDestination
import soy.gabimoreno.danielnolasco.ui.navigation.MainNavHost
import soy.gabimoreno.danielnolasco.ui.navigation.ProjectsNavDestination
import soy.gabimoreno.danielnolasco.ui.navigation.WalkingHomeNavDestination
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.domain.connectivity.ConnectivityStatus
import xyz.dnieln7.core.res.R

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val postNotificationsLauncher = permissionsLauncher(
        onGranted = { toastShort(R.string.thanks) }
    )

    @Inject
    lateinit var connectivityMonitor: ConnectivityMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermissions()

        setContent {
            val state by connectivityMonitor.connectivityStatus.collectAsState(
                ConnectivityStatus.UNKNOWN
            )

            DanielNolascoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(state)
                }
            }
        }
    }

    private fun initPermissions() {
        if (!checkPermissions(this, postNotificationsPermissions)) {
            toastShort(R.string.post_notifications_rationale)
            postNotificationsLauncher.launch(postNotificationsPermissions)
        }
    }
}

@Composable
fun MainScreen(connectivityStatus: ConnectivityStatus) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavItem(
                    iconRes = soy.gabimoreno.danielnolasco.R.drawable.ic_cat,
                    labelRes = R.string.cats,
                    navController = navController,
                    currentDestination = currentDestination,
                    destination = CatListNavDestination,
                )
                BottomNavItem(
                    iconRes = soy.gabimoreno.danielnolasco.R.drawable.ic_dog,
                    labelRes = R.string.dogs,
                    navController = navController,
                    currentDestination = currentDestination,
                    destination = DogListNavDestination,
                )
                BottomNavItem(
                    iconRes = soy.gabimoreno.danielnolasco.R.drawable.ic_walking,
                    labelRes = R.string.walking_app,
                    navController = navController,
                    currentDestination = currentDestination,
                    destination = WalkingHomeNavDestination,
                )
                BottomNavItem(
                    iconRes = soy.gabimoreno.danielnolasco.R.drawable.ic_portfolio,
                    labelRes = R.string.portfolio,
                    navController = navController,
                    currentDestination = currentDestination,
                    destination = ProjectsNavDestination,
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            MainNavHost(
                modifier = Modifier.weight(1F),
                navController = navController,
            )
            if (connectivityStatus != ConnectivityStatus.UNKNOWN) {
                ConnectivityBanner(connectivityStatus = connectivityStatus)
            }
        }
    }
}
