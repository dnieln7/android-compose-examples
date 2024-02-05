package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt
import kotlin.random.Random

// TODO: 03/02/24 Make a cytus clone
@Composable
fun BallClickerGame() {
    var points by remember { mutableIntStateOf(0) }
    var isTimerRunning by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(
                text = "Points: $points",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Button(
                onClick = {
                    isTimerRunning = !isTimerRunning
                    points = 0
                },
                content = {
                    Text(text = if (isTimerRunning) "Reset" else "Start")
                },
            )
            CountDownTimer(
                isTimerRunning = isTimerRunning,
                onEnd = { isTimerRunning = false },
            )
        }
        BallClicker(
            enabled = isTimerRunning,
            onBallClick = { points++ }
        )
    }
}

@Composable
fun CountDownTimer(
    time: Int = 30000,
    isTimerRunning: Boolean = false,
    onEnd: () -> Unit = {},
) {
    var currentTime by remember { mutableIntStateOf(time) }

    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (!isTimerRunning) {
            currentTime = time
            return@LaunchedEffect
        }

        if (currentTime > 0) {
            delay(1000)
            currentTime -= 1000
        } else {
            onEnd()
        }
    }
    Text(
        text = (currentTime / 1000).toString(),
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun BallClicker(
    radius: Float = 100F,
    enabled: Boolean = false,
    ballColor: Color = Color.Red,
    onBallClick: () -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        var ballPosition by remember {
            mutableStateOf(
                randomOffset(
                    radius = radius,
                    width = constraints.maxWidth,
                    height = constraints.maxHeight,
                )
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                // Need the coroutine to fire every time the game is enabled/disabled
                // When is enabled any touch input into the canvas will be processed
                .pointerInput(key1 = enabled) {// Similar to LaunchedEffect
                    if (!enabled) {
                        return@pointerInput
                    }

                    detectTapGestures {
                        val distance = sqrt(
                            (it.x - ballPosition.x).pow(2) + (it.y - ballPosition.y).pow(2)
                        )

                        // Check if the click point is inside the circle
                        if (distance <= radius) {
                            ballPosition = randomOffset(
                                radius = radius,
                                width = constraints.maxWidth,
                                height = constraints.maxHeight,
                            )
                            onBallClick()
                        }
                    }
                }
        ) {
            drawCircle(
                color = ballColor,
                radius = radius,
                center = ballPosition,
            )
        }
    }
}

private fun randomOffset(radius: Float, width: Int, height: Int): Offset {
    return Offset(
        x = Random.nextInt(
            from = radius.roundToInt(),         // Use radius to prevent the ball from going out of left boundaries
            until = width - radius.roundToInt(),// Use width radius to prevent the ball from going out of right boundaries
        ).toFloat(),
        y = Random.nextInt(
            from = radius.roundToInt(),
            until = height - radius.roundToInt(),
        ).toFloat(),
    )
}
