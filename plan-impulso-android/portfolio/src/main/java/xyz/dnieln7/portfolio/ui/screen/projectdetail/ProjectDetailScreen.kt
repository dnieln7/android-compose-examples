package xyz.dnieln7.portfolio.ui.screen.projectdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import xyz.dnieln7.core.res.R
import xyz.dnieln7.core.ui.UiState
import xyz.dnieln7.core.ui.composable.ButtonNavigationBack
import xyz.dnieln7.core.ui.composable.FullScreenCircularProgress
import xyz.dnieln7.core.ui.composable.FullScreenError
import xyz.dnieln7.core.ui.composable.TextTitlePrimary
import xyz.dnieln7.portfolio.domain.model.Project

@Composable
fun ProjectDetailScreen(
    projectDetailViewModel: ProjectDetailViewModel = hiltViewModel(),
    id: Int,
    navigateToEdit: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    val editButtonState by projectDetailViewModel.editButtonState.collectAsState()
    val state by projectDetailViewModel.uiState.collectAsState()

    LaunchedEffect(
        key1 = "updateEditButtonState",
        block = { projectDetailViewModel.updateEditButtonState() },
    )
    LaunchedEffect(key1 = "getProjectById", block = { projectDetailViewModel.getProjectById(id) })

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { ButtonNavigationBack(navigateBack = navigateBack) },
                title = {
                    if (state is UiState.Success) {
                        Text(text = (state as UiState.Success<Project>).data.name)
                    }
                },
            )
        },
        floatingActionButton = {
            EditProjectFAB(state = editButtonState, onClick = { navigateToEdit(id) })
        }
    ) {
        when (state) {
            UiState.Loading, UiState.None -> FullScreenCircularProgress(
                modifier = Modifier.padding(it),
            )

            is UiState.Error -> FullScreenError(
                modifier = Modifier.padding(it),
                message = (state as UiState.Error).message,
                onRetry = { projectDetailViewModel.getProjectById(id) }
            )

            is UiState.Success -> ProjectDetail(
                modifier = Modifier.padding(it),
                project = (state as UiState.Success).data,
            )
        }
    }
}

@Composable
fun EditProjectFAB(
    modifier: Modifier = Modifier,
    state: EditButtonState,
    onClick: () -> Unit,
) {
    if (state is EditButtonState.Visible) {
        FloatingActionButton(
            modifier = modifier,
            onClick = onClick,
        ) {
            Icon(
                painter = painterResource(xyz.dnieln7.portfolio.R.drawable.ic_edit),
                contentDescription = stringResource(R.string.edit_project)
            )
        }
    }
}

@Composable
fun ProjectDetail(
    modifier: Modifier = Modifier,
    project: Project,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .verticalScroll(scrollState),
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth(),
            model = project.thumbnail,
            contentDescription = null,
            error = painterResource(xyz.dnieln7.portfolio.R.drawable.ic_broken_image)
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextTitlePrimary(text = stringResource(R.string.ownership))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = project.ownership, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(12.dp))
        TextTitlePrimary(text = stringResource(R.string.duration))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = project.duration, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(12.dp))
        TextTitlePrimary(text = stringResource(R.string.description))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = project.description, style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(12.dp))
        TextTitlePrimary(text = stringResource(R.string.features))
        Spacer(modifier = Modifier.height(4.dp))
        for (feature in project.features) {
            Row {
                Icon(
                    painter = painterResource(xyz.dnieln7.portfolio.R.drawable.ic_check),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = feature, style = MaterialTheme.typography.body1)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        TextTitlePrimary(text = stringResource(R.string.technologies))
        Spacer(modifier = Modifier.height(4.dp))
        for (technology in project.technologies) {
            Row {
                Icon(
                    painter = painterResource(xyz.dnieln7.portfolio.R.drawable.ic_arrow_right),
                    tint = MaterialTheme.colors.primary,
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = technology, style = MaterialTheme.typography.body1)
            }
        }
    }
}
