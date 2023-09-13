package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListTileWalkingSession(
    modifier: Modifier = Modifier,
    walkingSession: WalkingSession,
    formatStartTime: (Long) -> String,
    formatDuration: (Long) -> String,
    onClick: (() -> Unit)?
) {
    if (onClick != null) {
        Card(
            modifier = modifier.fillMaxWidth(),
            elevation = 8.dp,
            onClick = onClick,
        ) {
            ListTileWalkingSessionContent(
                walkingSession = walkingSession,
                formatStartTime = formatStartTime,
                formatDuration = formatDuration,
            )
        }
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            elevation = 8.dp,
        ) {
            ListTileWalkingSessionContent(
                walkingSession = walkingSession,
                formatStartTime = formatStartTime,
                formatDuration = formatDuration,
            )
        }
    }
}

@Composable
private fun ListTileWalkingSessionContent(
    walkingSession: WalkingSession,
    formatStartTime: (Long) -> String,
    formatDuration: (Long) -> String,
) {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(soy.gabimoreno.danielnolasco.R.drawable.ic_walking),
            contentDescription = stringResource(R.string.walking_session),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = formatStartTime(walkingSession.startTime),
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatDuration(walkingSession.duration),
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ListTileWalkingSessionPreview() {
    DanielNolascoTheme {
        ListTileWalkingSession(
            walkingSession = WalkingSession(
                startTime = 1,
                endTime = null,
                duration = 1,
                locationEvents = emptyList()
            ),
            formatStartTime = { "April 1 2023" },
            formatDuration = { "1 seconds" },
            onClick = null
        )
    }
}
