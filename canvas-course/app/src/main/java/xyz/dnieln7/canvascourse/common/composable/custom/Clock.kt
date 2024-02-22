package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.ZonedDateTime
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Clock(modifier: Modifier = Modifier, radius: Dp = 100.dp) {
    var now by remember { mutableLongStateOf(0) }

    var hours by remember { mutableIntStateOf(0) }
    var minutes by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(0) }

    LaunchedEffect(key1 = now) {
        val zonedDateTime = ZonedDateTime.now()

        hours = zonedDateTime.hour
        minutes = zonedDateTime.minute
        seconds = zonedDateTime.second

        delay(1000)

        now = zonedDateTime.toEpochSecond()
    }

    Canvas(
        modifier = modifier
            .size(radius * 2)
    ) {
        val radiusPx = radius.toPx()

        val singleMinuteLineLength = radiusPx * 0.05F
        val fiveMinuteLineLength = singleMinuteLineLength * 2

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                center.x,
                center.y,
                radiusPx,
                NativePaint().apply {
                    color = NativeColor.WHITE
                    style = NativeSPaintStyle.FILL

                    setShadowLayer(
                        /* radius = */ 40F,
                        /* dx = */ 0F,
                        /* dy = */ 0F,
                        /* shadowColor = */ NativeColor.argb(50, 0, 0, 0),
                    )
                }
            )
        }

        for (i in 1..60) {
            val angleInRadians = ((i - 1) * 6) * (PI / 180F)

            val lineLength =
                if ((i - 1) % 5 == 0) fiveMinuteLineLength else singleMinuteLineLength

            val start = Offset(
                x = radiusPx * cos(angleInRadians).toFloat() + center.x,
                y = radiusPx * sin(angleInRadians).toFloat() + center.y,
            )

            val end = Offset(
                x = (radiusPx - lineLength) * cos(angleInRadians).toFloat() + center.x,
                y = (radiusPx - lineLength) * sin(angleInRadians).toFloat() + center.y,
            )

            drawLine(
                color = Color.Black,
                start = start,
                end = end,
            )
        }

        val hourAngleInRadians = ((hours - 3) * 30) * (PI / 180F)
        val hourLineLength = radiusPx / 2
        val hourLineWidth = radiusPx * 0.06F

        drawLine(
            color = Color.Black,
            start = center,
            end = Offset(
                x = hourLineLength * cos(hourAngleInRadians).toFloat() + center.x,
                y = hourLineLength * sin(hourAngleInRadians).toFloat() + center.y,
            ),
            strokeWidth = hourLineWidth,
        )

        val minuteAngleInRadians = ((minutes - 15) * 6) * (PI / 180F)
        val minuteLineLength = radiusPx / 1.2F
        val minuteLineWidth = radiusPx * 0.04F

        drawLine(
            color = Color.Black,
            start = center,
            end = Offset(
                x = minuteLineLength * cos(minuteAngleInRadians).toFloat() + center.x,
                y = minuteLineLength * sin(minuteAngleInRadians).toFloat() + center.y,
            ),
            strokeWidth = minuteLineWidth,
        )

        val secondAngleInRadians = ((seconds - 15) * 6) * (PI / 180F)
        val secondLineLength = radiusPx / 1.2F
        val secondLineWidth = radiusPx * 0.02F

        drawLine(
            color = Color.Red,
            start = center,
            end = Offset(
                x = secondLineLength * cos(secondAngleInRadians).toFloat() + center.x,
                y = secondLineLength * sin(secondAngleInRadians).toFloat() + center.y,
            ),
            strokeWidth = secondLineWidth,
        )

        val centerCircleRadius = radiusPx * 0.06F

        drawCircle(
            color = Color.Red,
            radius = centerCircleRadius,
            center = center,
        )
    }
}
