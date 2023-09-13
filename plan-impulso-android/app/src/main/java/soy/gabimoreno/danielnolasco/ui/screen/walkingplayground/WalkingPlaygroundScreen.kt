package soy.gabimoreno.danielnolasco.ui.screen.walkingplayground

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent
import soy.gabimoreno.danielnolasco.framework.broadcastreceiver.ComposableBroadcastReceiver
import soy.gabimoreno.danielnolasco.framework.extensions.buildIntentSenderRequest
import soy.gabimoreno.danielnolasco.framework.extensions.toastLong
import soy.gabimoreno.danielnolasco.framework.location.composableLocationSettingsLauncher
import soy.gabimoreno.danielnolasco.framework.service.LocationService
import soy.gabimoreno.danielnolasco.framework.service.startForegroundService
import soy.gabimoreno.danielnolasco.ui.composable.DialogYesNo
import soy.gabimoreno.danielnolasco.ui.composable.ListTileLocationEvent
import xyz.dnieln7.core.res.R

@Composable
fun WalkingPlaygroundScreen(
    walkingPlaygroundViewModel: WalkingPlaygroundViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val context = LocalContext.current

    val state by walkingPlaygroundViewModel.uiState.collectAsState()
    val locationEvents by state.locationEvents.collectAsState(initial = emptyList())
    val locationSettingsLauncher = composableLocationSettingsLauncher(
        onGranted = { walkingPlaygroundViewModel.startSession() },
        onDenied = {},
    )

    ComposableBroadcastReceiver(LocationService.ACTION_STARTED) {
        walkingPlaygroundViewModel.onServiceStarted()
    }

    ComposableBroadcastReceiver(LocationService.ACTION_TIMER_TICK) { intent ->
        intent.getLongExtra(LocationService.EXTRA_TIMER_SECONDS, DEFAULT_TIMER_SECONDS).let {
            if (it != DEFAULT_TIMER_SECONDS) {
                walkingPlaygroundViewModel.onTimerTick(it)
            }
        }
    }

    if (!state.hasLocationCapabilities) {
        val request = state.locationCapabilitiesException?.buildIntentSenderRequest()

        if (request != null) {
            locationSettingsLauncher.launch(request)
        } else {
            context.toastLong(R.string.location_not_available)
            navigateBack()
        }
    }

    if (state.isReady) {
        walkingPlaygroundViewModel.startTime?.let {
            val intent = Intent(context, LocationService::class.java).apply {
                putExtra(LocationService.EXTRA_START_TIME, it)
            }

            startForegroundService(context, intent)
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
        ) {
            WalkingPlaygroundHeader(
                isStarted = state.isStarted,
                seconds = state.seconds,
                start = { walkingPlaygroundViewModel.startSession() },
                finish = {
                    context.stopService(Intent(context, LocationService::class.java))
                    walkingPlaygroundViewModel.finishSession()
                    navigateBack()
                }
            )
            WalkingPlaygroundList(
                modifier = Modifier.weight(1F),
                locationEvents = locationEvents,
            )
        }
    }
}

@Composable
fun WalkingPlaygroundHeader(
    modifier: Modifier = Modifier,
    isStarted: Boolean,
    seconds: String,
    start: () -> Unit,
    finish: () -> Unit,
) {
    var showExitDialog by rememberSaveable { mutableStateOf(false) }

    if (showExitDialog) {
        DialogYesNo(
            title = R.string.are_you_sure,
            message = R.string.exit_walking_session,
            onYes = { finish() },
            dismiss = { showExitDialog = false },
        )
    }

    Row(
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = seconds)
        if (isStarted) {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { showExitDialog = true },
                painter = painterResource(soy.gabimoreno.danielnolasco.R.drawable.ic_stop),
                contentDescription = stringResource(R.string.stop),
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .clickable { start() },
                painter = painterResource(soy.gabimoreno.danielnolasco.R.drawable.ic_play),
                contentDescription = stringResource(R.string.start),
            )
        }
    }
}

@Composable
fun WalkingPlaygroundList(modifier: Modifier = Modifier, locationEvents: List<LocationEvent>) {
    if (locationEvents.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.empty_location_events),
                style = MaterialTheme.typography.caption,
            )
        }
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(locationEvents) {
                Box(modifier = Modifier.padding(4.dp)) { ListTileLocationEvent(it) }
            }
        }
    }
}

private const val DEFAULT_TIMER_SECONDS = -1L
