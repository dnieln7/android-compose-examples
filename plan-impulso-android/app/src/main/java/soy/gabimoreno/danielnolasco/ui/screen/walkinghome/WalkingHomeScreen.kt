package soy.gabimoreno.danielnolasco.ui.screen.walkinghome

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.framework.permissions.checkPermissions
import soy.gabimoreno.danielnolasco.framework.permissions.composablePermissionsLauncher
import soy.gabimoreno.danielnolasco.framework.permissions.locationPermissions
import soy.gabimoreno.danielnolasco.ui.composable.ButtonIcon
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@Composable
fun WalkingHomeScreen(
    navigateToWalkingPlayground: () -> Unit,
    navigateToWalkingHistory: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Icon(
                modifier = Modifier.size(72.dp),
                painter = painterResource(soy.gabimoreno.danielnolasco.R.drawable.ic_walking),
                contentDescription = stringResource(R.string.walking_app),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(R.string.walking_app))
            Column(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                NavigateToPlaygroundButton(
                    scaffoldState = scaffoldState,
                    navigateToWalkingPlayground = navigateToWalkingPlayground,
                )
                Spacer(modifier = Modifier.height(12.dp))
                ButtonIcon(
                    icon = soy.gabimoreno.danielnolasco.R.drawable.ic_history,
                    label = R.string.history,
                    onClick = navigateToWalkingHistory,
                )
            }
        }
    }
}

@Composable
fun NavigateToPlaygroundButton(
    scaffoldState: ScaffoldState,
    navigateToWalkingPlayground: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val permissionsRequired = stringResource(R.string.permissions_required)

    val locationLauncher = composablePermissionsLauncher(
        onGranted = { navigateToWalkingPlayground() },
        onDenied = {
            coroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(permissionsRequired)
            }
        }
    )

    ButtonIcon(
        icon = soy.gabimoreno.danielnolasco.R.drawable.ic_walking,
        label = R.string.start_walking,
        onClick = {
            if (checkPermissions(context, locationPermissions)) {
                navigateToWalkingPlayground()
            } else {
                locationLauncher.launch(locationPermissions)
            }
        },
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WalkingHomeScreenPreview() {
    DanielNolascoTheme {
        WalkingHomeScreen(navigateToWalkingPlayground = {}, navigateToWalkingHistory = {})
    }
}
