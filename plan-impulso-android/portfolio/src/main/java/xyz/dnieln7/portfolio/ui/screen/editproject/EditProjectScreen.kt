package xyz.dnieln7.portfolio.ui.screen.editproject

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import xyz.dnieln7.core.res.R
import xyz.dnieln7.core.ui.composable.ButtonNavigationBack
import xyz.dnieln7.core.ui.composable.FullScreenCircularProgress
import xyz.dnieln7.core.ui.composable.FullScreenError
import xyz.dnieln7.core.ui.composable.SnackBar
import xyz.dnieln7.portfolio.domain.model.Project

@Composable
fun EditProjectScreen(
    editProjectViewModel: EditProjectViewModel = hiltViewModel(),
    id: Int,
    navigateBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    val state by editProjectViewModel.uiState.collectAsState()

    if (state == EditProjectState.None) {
        LaunchedEffect(key1 = "getProjectById", block = { editProjectViewModel.getProjectById(id) })
    }

    if (state is EditProjectState.Success && (state as EditProjectState.Success).update) {
        SnackBar(
            coroutineScope = coroutineScope,
            scaffoldState = scaffoldState,
            message = stringResource(R.string.project_updated),
            onDismissed = {
                editProjectViewModel.onProjectUpdated((state as EditProjectState.Success).data)
            }
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = { ButtonNavigationBack(navigateBack = navigateBack) },
                title = { Text(text = stringResource(R.string.edit_project)) },
            )
        },
    ) {
        when (state) {
            EditProjectState.None, EditProjectState.Loading -> FullScreenCircularProgress(
                modifier = Modifier.padding(it)
            )

            EditProjectState.NotAuthenticated -> FullScreenError(
                modifier = Modifier.padding(it),
                message = stringResource(R.string.session_is_no_longer_valid),
                onRetryMessage = stringResource(R.string.go_back),
                onRetry = navigateBack
            )

            is EditProjectState.UpdateError -> FullScreenError(
                modifier = Modifier.padding(it),
                message = (state as EditProjectState.UpdateError).message,
                onRetry = {
                    editProjectViewModel.updateProject(
                        (state as EditProjectState.UpdateError).project
                    )
                }
            )

            is EditProjectState.Error -> FullScreenError(
                modifier = Modifier.padding(it),
                message = (state as EditProjectState.Error).message,
                onRetry = { editProjectViewModel.getProjectById(id) }
            )

            is EditProjectState.Success -> EditProject(
                modifier = Modifier.padding(it),
                project = (state as EditProjectState.Success).data,
                updateProject = { project -> editProjectViewModel.updateProject(project) }
            )
        }
    }
}

@Composable
fun EditProject(
    modifier: Modifier = Modifier,
    project: Project,
    updateProject: (Project) -> Unit,
) {
    val scrollState = rememberScrollState()

    var ownership by rememberSaveable { mutableStateOf(project.ownership) }
    var duration by rememberSaveable { mutableStateOf(project.duration) }
    var description by rememberSaveable { mutableStateOf(project.description) }

    val validated by rememberSaveable {
        mutableStateOf(ownership.isNotBlank() && duration.isNotBlank() && description.isNotBlank())
    }

    Column(
        modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = ownership,
            onValueChange = { ownership = it },
            label = { Text(text = stringResource(R.string.ownership)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text(text = stringResource(R.string.duration)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(text = stringResource(R.string.description)) },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                val newProject = project.copy(
                    ownership = ownership,
                    duration = duration,
                    description = description,
                )
                updateProject(newProject)
            },
            enabled = validated,
            content = { Text(text = stringResource(R.string.save_changes)) },
        )
    }
}
