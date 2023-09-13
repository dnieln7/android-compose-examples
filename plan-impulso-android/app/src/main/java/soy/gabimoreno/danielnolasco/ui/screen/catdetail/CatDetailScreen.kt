package soy.gabimoreno.danielnolasco.ui.screen.catdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.model.Cat
import soy.gabimoreno.danielnolasco.framework.permissions.checkPermissions
import soy.gabimoreno.danielnolasco.framework.permissions.composablePermissionsLauncher
import soy.gabimoreno.danielnolasco.framework.permissions.postNotificationsPermissions
import soy.gabimoreno.danielnolasco.ui.composable.FullScreenCircularProgress
import soy.gabimoreno.danielnolasco.ui.composable.FullScreenError
import soy.gabimoreno.danielnolasco.ui.composable.LevelIndicator
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R
import xyz.dnieln7.core.ui.composable.ButtonNavigationBack

@Composable
fun CatDetailScreen(
    catDetailViewModel: CatDetailViewModel = hiltViewModel(),
    name: String,
    navigateBack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val state by catDetailViewModel.catDetailState.collectAsState()

    LaunchedEffect(key1 = "getCatByName", block = { catDetailViewModel.getCatByName(name) })

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = { ButtonNavigationBack(navigateBack = navigateBack) },
                title = { Text(text = name) },
            )
        },
        floatingActionButton = {
            CatFAB(
                state = state,
                scaffoldState = scaffoldState,
                onClick = { catDetailViewModel.displayInNotification(it) },
            )
        }
    ) {
        when (state) {
            CatDetailState.Loading -> FullScreenCircularProgress(
                modifier = Modifier.padding(it),
            )
            is CatDetailState.Error -> FullScreenError(
                modifier = Modifier.padding(it),
                messageRes = (state as CatDetailState.Error).messageRes,
                message = (state as CatDetailState.Error).message,
                onRetry = { catDetailViewModel.getCatByName(name) }
            )
            is CatDetailState.Success -> CatDetail(
                modifier = Modifier.padding(it),
                cat = (state as CatDetailState.Success).data,
                getLevelIndicatorLevel = { rawLevel ->
                    catDetailViewModel.getLevelIndicatorLevel(rawLevel)
                },
                getLevelIndicatorColor = { level -> catDetailViewModel.getLevelIndicatorColor(level) }
            )
        }
    }
}

@Composable
fun CatDetail(
    modifier: Modifier,
    cat: Cat,
    getLevelIndicatorLevel: (Int) -> Float,
    getLevelIndicatorColor: (Float) -> Color,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp),
    ) {
        Text(text = stringResource(R.string.place_of_origin_placeholder, cat.name))
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.playfulness_level))
        Spacer(modifier = Modifier.height(8.dp))
        LevelIndicator(
            level = getLevelIndicatorLevel(cat.playfulness),
            getLevelIndicatorColor = getLevelIndicatorColor
        )
    }
}

@Composable
fun CatFAB(
    modifier: Modifier = Modifier,
    state: CatDetailState,
    scaffoldState: ScaffoldState,
    onClick: (Cat) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    if (state is CatDetailState.Success) {
        val context = LocalContext.current
        val permissionsRequired = stringResource(R.string.permissions_required)

        val postNotificationsLauncher = composablePermissionsLauncher(
            onGranted = { onClick(state.data) },
            onDenied = {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(permissionsRequired)
                }
            }
        )

        FloatingActionButton(
            modifier = modifier,
            onClick = {
                if (checkPermissions(context, postNotificationsPermissions)) {
                    onClick(state.data)
                } else {
                    postNotificationsLauncher.launch(postNotificationsPermissions)
                }
            },
        ) {
            Icon(
                painter = painterResource(
                    soy.gabimoreno.danielnolasco.R.drawable.ic_add_notification
                ),
                contentDescription = stringResource(R.string.display_in_notification)
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CatDetailScreenPreview() {
    DanielNolascoTheme {
        CatDetailScreen(name = "Greece", navigateBack = {})
    }
}
