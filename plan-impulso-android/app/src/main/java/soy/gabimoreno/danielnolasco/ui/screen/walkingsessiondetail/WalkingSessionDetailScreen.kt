package soy.gabimoreno.danielnolasco.ui.screen.walkingsessiondetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession
import soy.gabimoreno.danielnolasco.ui.composable.*
import xyz.dnieln7.core.res.R
import xyz.dnieln7.core.ui.composable.ButtonNavigationBack

@Composable
fun WalkingSessionDetailScreen(
    walkingSessionDetailViewModel: WalkingSessionDetailViewModel = hiltViewModel(),
    startTime: Long,
    navigateBack: () -> Unit,
) {
    val state by walkingSessionDetailViewModel.uiState.collectAsState()
    var listMode by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(
        key1 = "getWalkingSessionByStartTime",
        block = { walkingSessionDetailViewModel.getWalkingSessionByStartTime(startTime) },
    )

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = { ButtonNavigationBack(navigateBack = navigateBack) },
                title = { Text(text = stringResource(R.string.session_details)) },
                actions = {
                    if (listMode) {
                        Icon(
                            modifier = Modifier.clickable { listMode = false },
                            painter = painterResource(
                                soy.gabimoreno.danielnolasco.R.drawable.ic_map
                            ),
                            contentDescription = stringResource(R.string.change_to_map_mode),
                        )
                    } else {
                        Icon(
                            modifier = Modifier.clickable { listMode = true },
                            painter = painterResource(
                                soy.gabimoreno.danielnolasco.R.drawable.ic_list_mode
                            ),
                            contentDescription = stringResource(R.string.change_to_list_mode),
                        )
                    }
                }
            )
        },
    ) {
        when (state) {
            WalkingSessionDetailState.Loading -> FullScreenCircularProgress(
                modifier = Modifier.padding(it),
            )
            is WalkingSessionDetailState.Error -> FullScreenError(
                modifier = Modifier.padding(it),
                messageRes = (state as WalkingSessionDetailState.Error).messageRes,
                message = (state as WalkingSessionDetailState.Error).message,
                onRetry = { walkingSessionDetailViewModel.getWalkingSessionByStartTime(startTime) }
            )
            is WalkingSessionDetailState.Success -> WalkingSessionDetail(
                modifier = Modifier.padding(it),
                listMode = listMode,
                walkingSession = (state as WalkingSessionDetailState.Success).data,
                formatStartTime = { startTime ->
                    walkingSessionDetailViewModel.formatStartTime(startTime)
                },
                formatDuration = { duration ->
                    walkingSessionDetailViewModel.formatDuration(duration)
                },
            )
        }
    }
}

@Composable
private fun WalkingSessionDetail(
    modifier: Modifier,
    listMode: Boolean,
    walkingSession: WalkingSession,
    formatStartTime: (Long) -> String,
    formatDuration: (Long) -> String,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        ListTileWalkingSession(
            walkingSession = walkingSession,
            formatStartTime = formatStartTime,
            formatDuration = formatDuration,
            onClick = null,
        )
        if (walkingSession.locationEvents.isNotEmpty()) {
            if (listMode) {
                LocationEventsList(
                    modifier = Modifier.weight(1F),
                    locationEvents = walkingSession.locationEvents,
                )
            } else {
                LocationEventsMap(
                    modifier = Modifier.weight(1F),
                    locationEvents = walkingSession.locationEvents,
                )
            }
        } else {
            Box(
                modifier = Modifier.weight(1F),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.no_location_events),
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
private fun LocationEventsList(modifier: Modifier = Modifier, locationEvents: List<LocationEvent>) {
    LazyColumn(modifier = modifier) {
        items(locationEvents) {
            Box(modifier = Modifier.padding(4.dp)) {
                ListTileLocationEvent(it)
            }
        }
    }
}

@Composable
private fun LocationEventsMap(modifier: Modifier = Modifier, locationEvents: List<LocationEvent>) {
    val points = locationEvents.map { LatLng(it.latitude, it.longitude) }

    val mapUiSettings = MapUiSettings(
        mapToolbarEnabled = false,
        myLocationButtonEnabled = false,
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(points.first(), MAP_ZOOM)
    }

    GoogleMap(
        modifier = modifier,
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState
    ) {
        Polyline(
            points = points,
            width = 15F,
            jointType = JointType.ROUND,
            color = MaterialTheme.colors.primary
        )
    }
}

private const val MAP_ZOOM = 15F
