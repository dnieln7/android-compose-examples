package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import xyz.dnieln7.core.domain.connectivity.ConnectivityStatus

@Composable
fun ConnectivityBanner(modifier: Modifier = Modifier, connectivityStatus: ConnectivityStatus) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()

    var visible by rememberSaveable { mutableStateOf(false) }
    var lastConnectivityStatus by rememberSaveable { mutableStateOf(ConnectivityStatus.CONNECTED) }

    val colors = if (connectivityStatus == ConnectivityStatus.CONNECTED) {
        Color.Green to Color.Black
    } else {
        Color.Red to Color.White
    }

    if (connectivityStatus == ConnectivityStatus.CONNECTED) {
        if (lastConnectivityStatus != connectivityStatus) {
            LaunchedEffect(key1 = connectivityStatus, block = {
                coroutineScope.launch {
                    visible = true
                    delay(ANIMATION_DURATION * DELAY_MULTIPLIER)
                    visible = false
                }
            })
        }
    } else {
        visible = true
    }

    lastConnectivityStatus = connectivityStatus

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing),
            initialOffsetY = { with(density) { +25.dp.roundToPx() } }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = ANIMATION_DURATION, easing = LinearEasing),
            targetOffsetY = { with(density) { +25.dp.roundToPx() } }
        )
    ) {
        Box(
            modifier = modifier
                .background(colors.first)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = connectivityStatus.name,
                style = MaterialTheme.typography.caption.copy(color = colors.second)
            )
        }
    }
}

private const val ANIMATION_DURATION = 500
private const val DELAY_MULTIPLIER = 3L
