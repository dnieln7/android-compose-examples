package xyz.dnieln7.portfolio.ui.screen.projects

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import xyz.dnieln7.core.res.R
import xyz.dnieln7.core.ui.composable.FullScreenCircularProgress
import xyz.dnieln7.core.ui.composable.FullScreenError
import xyz.dnieln7.core.ui.composable.SnackBar
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.ui.composable.GridTileProject
import xyz.dnieln7.portfolio.ui.composable.SwipeGridTileProject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProjectsScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    projectsViewModel: ProjectsViewModel = hiltViewModel(),
    navigateToProjectDetail: (Int) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val authState by authViewModel.uiState.collectAsState()
    val projectsState by projectsViewModel.uiState.collectAsState()

    LaunchedEffect(key1 = "getProjects", block = { projectsViewModel.getProjects() })
    LaunchedEffect(key1 = "checkAuth", block = { authViewModel.checkUserSession() })

    if (authState is AuthState.LoginError) {
        SnackBar(
            coroutineScope = coroutineScope,
            scaffoldState = scaffoldState,
            message = (authState as AuthState.LoginError).message,
            onDismissed = { authViewModel.onLoginErrorShown() }
        )
    }

    projectsState.deleted?.let { project ->
        SnackBar(
            coroutineScope = coroutineScope,
            scaffoldState = scaffoldState,
            message = stringResource(R.string.project_placeholder_deleted, project.name),
            onDismissed = { projectsViewModel.resetDeleteState() }
        )
    }

    projectsState.deleteError?.let { error ->
        SnackBar(
            coroutineScope = coroutineScope,
            scaffoldState = scaffoldState,
            message = error,
            onDismissed = { projectsViewModel.resetDeleteState() }
        )
    }

    if (projectsState.deleteBlocked) {
        SnackBar(
            coroutineScope = coroutineScope,
            scaffoldState = scaffoldState,
            message = stringResource(R.string.session_is_no_longer_valid),
            onDismissed = { projectsViewModel.resetDeleteState() }
        )
    }

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            BottomSheetLogin(
                hideLogin = { coroutineScope.launch { modalSheetState.hide() } },
                onLogin = { email, password -> authViewModel.login(email, password) }
            )
        },
        content = {
            Scaffold(
                scaffoldState = scaffoldState,
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(R.string.projects)) },
                        actions = {
                            ActionLogin(
                                authState = authState,
                                showLogin = { coroutineScope.launch { modalSheetState.show() } },
                                onLogout = { authViewModel.logout() }
                            )
                        }
                    )
                }
            ) {
                if (projectsState.loading) {
                    FullScreenCircularProgress(Modifier.padding(it))
                } else {
                    projectsState.error?.let { message ->
                        FullScreenError(
                            modifier = Modifier.padding(it),
                            message = message,
                            onRetry = { projectsViewModel.getProjects() },
                        )
                    }

                    projectsState.data?.let { projects ->
                        ProjectsList(
                            modifier = Modifier.padding(it),
                            projects = projects,
                            onRefresh = { projectsViewModel.refreshProjects() },
                            navigateToProjectDetail = navigateToProjectDetail,
                            useSwipeGridTiles = authState == AuthState.LoggedIn,
                            onDelete = { project -> projectsViewModel.deleteProject(project) },
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun BottomSheetLogin(
    modifier: Modifier = Modifier,
    hideLogin: () -> Unit,
    onLogin: (String, String) -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.enter_your_credentials),
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.email)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = stringResource(R.string.password)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = {
                onLogin(email, password)
                hideLogin()
            },
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
            content = { Text(text = stringResource(R.string.login)) },
        )
    }
}

@Composable
fun ActionLogin(
    authState: AuthState,
    showLogin: () -> Unit,
    onLogout: () -> Unit,
) {
    when (authState) {
        AuthState.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colors.onSurface,
                strokeWidth = 2.dp,
            )
        }

        AuthState.LoggedIn, AuthState.LogoutError -> {
            Icon(
                modifier = Modifier.clickable { onLogout() },
                painter = painterResource(
                    xyz.dnieln7.portfolio.R.drawable.ic_logout
                ),
                contentDescription = stringResource(R.string.logout),
            )
        }

        AuthState.LoggedOut, is AuthState.LoginError -> {
            Icon(
                modifier = Modifier.clickable { showLogin() },
                painter = painterResource(
                    xyz.dnieln7.portfolio.R.drawable.ic_login
                ),
                contentDescription = stringResource(R.string.login),
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProjectsList(
    modifier: Modifier,
    projects: List<Project>,
    onRefresh: () -> Unit,
    navigateToProjectDetail: (Int) -> Unit,
    useSwipeGridTiles: Boolean,
    onDelete: (Project) -> Unit,
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        onRefresh()
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    Box(Modifier.pullRefresh(refreshState)) {
        LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
            items(items = projects, key = { it.id }) { project ->
                if (useSwipeGridTiles) {
                    SwipeGridTileProject(
                        project = project,
                        onClick = { navigateToProjectDetail(project.id) },
                        onDelete = { onDelete(project) }
                    )
                } else {
                    GridTileProject(
                        project = project,
                        onClick = { navigateToProjectDetail(project.id) },
                    )
                }
            }
        }

        PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}
