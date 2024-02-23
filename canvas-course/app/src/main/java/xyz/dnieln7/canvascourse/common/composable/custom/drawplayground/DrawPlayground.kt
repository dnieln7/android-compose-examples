package xyz.dnieln7.canvascourse.common.composable.custom.drawplayground

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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun DrawPlayGround() {
    val tipRadius = 32F

    var segments: List<Segment> by remember { mutableStateOf(emptyList()) }

    var size by remember { mutableStateOf(Size.Unspecified) }

    var temporalSegmentStart by remember { mutableStateOf(Offset.Unspecified) }
    var temporalSegmentEnd by remember { mutableStateOf(Offset.Unspecified) }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { position ->
                        if (segments.isNotEmpty()) {
                            val endDeltaX = (position.x - segments.last().endPosition.x).pow(2)
                            val endDeltaY = (position.y - segments.last().endPosition.y).pow(2)
                            val endDistance = sqrt(endDeltaX + endDeltaY)

                            if (endDistance <= tipRadius * 2.5F) {
                                temporalSegmentStart = position
                            }
                        } else {
                            temporalSegmentStart = position
                        }
                    },
                    onDragEnd = {
                        if (temporalSegmentEnd == Offset.Unspecified) {
                            temporalSegmentStart = Offset.Unspecified
                            return@detectDragGestures
                        }

                        val newSegment = Segment(
                            id = System.currentTimeMillis(),
                            startPosition = temporalSegmentStart,
                            endPosition = temporalSegmentEnd,
                        )

                        temporalSegmentStart = Offset.Unspecified
                        temporalSegmentEnd = Offset.Unspecified

                        segments = segments + newSegment
                    },
                ) { change, _ ->
                    if (temporalSegmentStart != Offset.Unspecified) {
                        val newChangePosition = change.position.run {
                            Offset(
                                x = x.coerceIn(
                                    minimumValue = 0F,
                                    maximumValue = size.width + (tipRadius * 2)
                                ),
                                y = y.coerceIn(
                                    minimumValue = 0F,
                                    maximumValue = size.height + (tipRadius * 2),
                                )
                            )
                        }

                        temporalSegmentEnd = newChangePosition
                    }
                }
            },
    ) {
        if (size == Size.Unspecified) {
            size = this.size
        }

        val segmentsPath = Path().apply {
            for (i in segments.indices) {

                if (i == 0) {
                    moveTo(segments[i].startPosition.x, segments[i].startPosition.y)
                } else {
                    lineTo(segments[i].startPosition.x, segments[i].startPosition.y)
                }

                lineTo(segments[i].endPosition.x, segments[i].endPosition.y)
            }
        }

        val temporalSegmentPath = Path().apply {
            if (temporalSegmentStart != Offset.Unspecified) {
                moveTo(temporalSegmentStart.x, temporalSegmentStart.y)
            }

            if (temporalSegmentEnd != Offset.Unspecified) {
                lineTo(temporalSegmentEnd.x, temporalSegmentEnd.y)
            }
        }

        drawPath(
            path = segmentsPath,
            color = Color.Black,
            style = Stroke(
                width = 16F,
                cap = StrokeCap.Round,
            ),
        )

        drawPath(
            path = temporalSegmentPath,
            color = Color.Black.copy(alpha = 0.50F),
            style = Stroke(
                width = 16F,
                cap = StrokeCap.Round,
            ),
        )

        if (segments.isNotEmpty()) {
            drawCircle(
                color = Color.Red,
                center = segments.first().startPosition,
                radius = tipRadius,
            )

            drawCircle(
                color = Color.Green,
                center = segments.last().endPosition,
                radius = tipRadius,
            )
        }
    }
}