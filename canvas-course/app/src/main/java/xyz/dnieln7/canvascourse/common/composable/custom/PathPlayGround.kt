package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun PathPlayGround() {
    val tipRadius = 32F
    val controlTipRadius = 64F

    var startPosition by remember { mutableStateOf(Offset(x = 100F, y = 100F)) }
    var endPosition by remember { mutableStateOf(Offset(x = 400F, y = 400F)) }

    var controlPoint1Position by remember { mutableStateOf(Offset.Zero) }
    var controlPoint2Position by remember { mutableStateOf(Offset.Zero) }

    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures { change, dragAmount ->

                    val startDeltaX = (change.position.x - startPosition.x).pow(2)
                    val startDeltaY = (change.position.y - startPosition.y).pow(2)
                    val startDistance = sqrt(startDeltaX + startDeltaY)

                    if (startDistance <= tipRadius * 2.5F) {
                        startPosition += dragAmount
                        return@detectDragGestures
                    }

                    val endDeltaX = (change.position.x - endPosition.x).pow(2)
                    val endDeltaY = (change.position.y - endPosition.y).pow(2)
                    val endDistance = sqrt(endDeltaX + endDeltaY)

                    if (endDistance <= tipRadius * 2.5F) {
                        endPosition += dragAmount
                        return@detectDragGestures
                    }

                    val controlPoint1DeltaX = (change.position.x - controlPoint1Position.x).pow(2)
                    val controlPoint1DeltaY = (change.position.y - controlPoint1Position.y).pow(2)
                    val controlPoint1Distance = sqrt(controlPoint1DeltaX + controlPoint1DeltaY)

                    if (controlPoint1Distance <= controlTipRadius * 2.5F) {
                        controlPoint1Position += dragAmount
                        return@detectDragGestures
                    }

                    val controlPoint2DeltaX = (change.position.x - controlPoint2Position.x).pow(2)
                    val controlPoint2DeltaY = (change.position.y - controlPoint2Position.y).pow(2)
                    val controlPoint2Distance = sqrt(controlPoint2DeltaX + controlPoint2DeltaY)

                    if (controlPoint2Distance <= controlTipRadius * 2.5F) {
                        controlPoint2Position += dragAmount
                        return@detectDragGestures
                    }
                }
            },
    ) {
        if (controlPoint1Position == Offset.Zero) {
            controlPoint1Position = Offset(
                x = center.x - 100F,
                y = center.y,
            )
        }

        if (controlPoint2Position == Offset.Zero) {
            controlPoint2Position = Offset(
                x = center.x + 100F,
                y = center.y,
            )
        }

        val path = Path().apply {
            moveTo(x = startPosition.x, y = startPosition.y)
            cubicTo(
                x1 = controlPoint1Position.x,
                y1 = controlPoint1Position.y,
                x2 = controlPoint2Position.x,
                y2 = controlPoint2Position.y,
                x3 = endPosition.x,
                y3 = endPosition.y
            )
        }

        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(
                width = 16F,
                cap = StrokeCap.Round,
            ),
        )

        drawCircle(
            color = Color.Red,
            center = startPosition,
            radius = tipRadius,
        )

        drawCircle(
            color = Color.Blue,
            center = endPosition,
            radius = tipRadius,
        )

        drawCircle(
            color = Color.Green,
            center = controlPoint1Position,
            radius = controlTipRadius,
        )

        drawText(
            textMeasurer = textMeasurer,
            text = "P1",
            topLeft = controlPoint1Position
        )

        drawCircle(
            color = Color.Green,
            center = controlPoint2Position,
            radius = controlTipRadius,
        )

        drawText(
            textMeasurer = textMeasurer,
            text = "P2",
            topLeft = controlPoint2Position
        )
    }
}