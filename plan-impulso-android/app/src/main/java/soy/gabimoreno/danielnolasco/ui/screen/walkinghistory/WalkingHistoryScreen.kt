package soy.gabimoreno.danielnolasco.ui.screen.walkinghistory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession
import soy.gabimoreno.danielnolasco.ui.composable.FullScreenCircularProgress
import soy.gabimoreno.danielnolasco.ui.composable.FullScreenError
import soy.gabimoreno.danielnolasco.ui.composable.ListTileWalkingSession
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R
import xyz.dnieln7.core.ui.composable.ButtonNavigationBack

@Composable
fun WalkingHistoryScreen(
    walkingHistoryViewModel: WalkingHistoryViewModel = hiltViewModel(),
    navigateToWalkingSessionDetail: (Long) -> Unit,
    navigateBack: () -> Unit,
) {
    val state by walkingHistoryViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { ButtonNavigationBack(navigateBack = navigateBack) },
                title = { Text(text = stringResource(R.string.history)) },
            )
        },
    ) {
        when (state) {
            WalkingHistoryState.Loading -> FullScreenCircularProgress(
                modifier = Modifier.padding(it),
            )
            is WalkingHistoryState.Error -> FullScreenError(
                modifier = Modifier.padding(it),
                messageRes = null,
                message = (state as WalkingHistoryState.Error).message,
                onRetry = { walkingHistoryViewModel.getWalkingSessions() }
            )
            is WalkingHistoryState.Success -> WalkingSessionList(
                modifier = Modifier.padding(it),
                walkingSessions = (state as WalkingHistoryState.Success).data,
                formatStartTime = { startTime ->
                    walkingHistoryViewModel.formatStartTime(startTime)
                },
                formatDuration = { duration ->
                    walkingHistoryViewModel.formatDuration(duration)
                },
                navigateToWalkingSessionDetail = navigateToWalkingSessionDetail,
            )
        }
    }
}

@Composable
fun WalkingSessionList(
    modifier: Modifier,
    walkingSessions: List<WalkingSession>,
    formatStartTime: (Long) -> String,
    formatDuration: (Long) -> String,
    navigateToWalkingSessionDetail: (Long) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(walkingSessions) {
            Box(modifier = Modifier.padding(4.dp)) {
                ListTileWalkingSession(
                    walkingSession = it,
                    formatStartTime = formatStartTime,
                    formatDuration = formatDuration,
                    onClick = { navigateToWalkingSessionDetail(it.startTime) }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WalkingHistoryScreenPreview() {
    DanielNolascoTheme {
        WalkingHistoryScreen(navigateToWalkingSessionDetail = {}, navigateBack = {})
    }
}
