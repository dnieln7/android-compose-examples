package soy.gabimoreno.danielnolasco.ui.screen.dogdetail

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
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.model.Dog
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
fun DogDetailScreen(
    dogDetailViewModel: DogDetailViewModel = hiltViewModel(),
    name: String,
    navigateBack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val state by dogDetailViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = "getDogByName", block = { dogDetailViewModel.getDogByName(name) })

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = { ButtonNavigationBack(navigateBack = navigateBack) },
                title = { Text(text = name) },
            )
        },
        floatingActionButton = {
            DogFAB(
                state = state,
                scaffoldState = scaffoldState,
                onClick = { dogDetailViewModel.displayInNotification(it) },
            )
        }
    ) {
        when (state) {
            DogDetailState.Loading -> FullScreenCircularProgress(
                modifier = Modifier.padding(it),
            )
            is DogDetailState.Error -> FullScreenError(
                modifier = Modifier.padding(it),
                messageRes = (state as DogDetailState.Error).messageRes,
                message = (state as DogDetailState.Error).message,
                onRetry = { dogDetailViewModel.getDogByName(name) }
            )
            is DogDetailState.Success -> DogDetail(
                modifier = Modifier.padding(it),
                dog = (state as DogDetailState.Success).data,
                getLevelIndicatorLevel = { rawLevel ->
                    dogDetailViewModel.getLevelIndicatorLevel(rawLevel)
                },
                getLevelIndicatorColor = { level -> dogDetailViewModel.getLevelIndicatorColor(level) }
            )
        }
    }
}

@Composable
fun DogDetail(
    modifier: Modifier,
    dog: Dog,
    getLevelIndicatorLevel: (Int) -> Float,
    getLevelIndicatorColor: (Float) -> Color,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = dog.imageLink,
            contentDescription = null,
            error = painterResource(soy.gabimoreno.danielnolasco.R.drawable.ic_broken_image)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = stringResource(R.string.playfulness_level))
        Spacer(modifier = Modifier.height(8.dp))
        LevelIndicator(
            level = getLevelIndicatorLevel(dog.playfulness),
            getLevelIndicatorColor = getLevelIndicatorColor
        )
    }
}

@Composable
fun DogFAB(
    modifier: Modifier = Modifier,
    state: DogDetailState,
    scaffoldState: ScaffoldState,
    onClick: (Dog) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    if (state is DogDetailState.Success) {
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
fun DogDetailScreenPreview() {
    DanielNolascoTheme {
        DogDetailScreen(name = "Greece", navigateBack = {})
    }
}
