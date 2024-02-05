package xyz.dnieln7.canvascourse.common.composable.custom.weightpicker

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun ScaleLegacy(
    modifier: Modifier = Modifier,
    style: ScaleStyle = ScaleStyle(),
    minWeight: Int = 0,
    maxWeight: Int = 100,
    initialWeight: Int = 50,
    onWeightChanged: (Int) -> Unit,
) {
    val radius = style.radius
    val scaleThickness = style.thickness

    var center by remember { mutableStateOf(Offset.Zero) }
    var circleCenter by remember { mutableStateOf(Offset.Zero) }

    var currentAngle by remember { mutableFloatStateOf(0F) }

    var dragStartedAngle by remember { mutableFloatStateOf(0F) }
    var oldAngle by remember { mutableFloatStateOf(currentAngle) }

    Canvas(
        modifier = Modifier
            .height(style.thickness * 2)
            .fillMaxWidth()
            // Never stop processing touch inputs
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = {
                        // Calculate the angle in which the drag started by creating
                        // a line between the circle center and the touch position then
                        // calculating the inverse tangent using the line's x and y delta
                        dragStartedAngle = -atan2(
                            y = circleCenter.x - it.x,
                            x = circleCenter.y - it.y,
                        ) * (180F / PI).toFloat()

                        println("dragStartedAngle: $dragStartedAngle")
                    },
                    onDragEnd = {
                        oldAngle = currentAngle
                        println("oldAngle: $oldAngle")
                    },
                    onDrag = { change, dragAmount ->
                        println("dragAmount: $dragAmount")

                        val endAngle = -atan2(
                            y = circleCenter.x - change.position.x,
                            x = circleCenter.y - change.position.y,
                        ) * (180F / PI).toFloat()

                        println("endAngle: $endAngle")

                        val newAngle = oldAngle + (endAngle - dragStartedAngle)

                        println("newAngle: $newAngle")

                        // Check if the new angle is between min and max, if less than min
                        // will return min, if more than max will return max
                        currentAngle = newAngle.coerceIn(
                            // invert the values because when incrementing the weigh the angle decreases
                            // and vice-versa
                            minimumValue = initialWeight - maxWeight.toFloat(),
                            maximumValue = initialWeight - minWeight.toFloat(),
                        )

                        onWeightChanged((initialWeight - currentAngle).roundToInt())
                    }
                )
            }
            .then(modifier)
    ) {
        center = this.center
        circleCenter = Offset(
            x = center.x,
            y = scaleThickness.toPx() / 2F + radius.toPx(),
        )

        // Use cale thickness / 2F because stroke expands in both sides, if only thickness is used
        // there will be unused space, with thickness / 2F we only add the top part of the expanded stroke

        // Radius that takes into account the top part of the expanded stroke
        val outerRadius = radius.toPx() + scaleThickness.toPx() / 2F

        // Radius that ignores the bottom part of the expanded stroke
        val innerRadius = radius.toPx() - scaleThickness.toPx() / 2F

        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                circleCenter.x,
                circleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleThickness.toPx()  // Expands in both sides; top and bottom
                    color = android.graphics.Color.WHITE

                    setStyle(Paint.Style.STROKE)
                    setShadowLayer(
                        /* radius = */ 40F,
                        /* dx = */ 0F,
                        /* dy = */ 0F,
                        /* shadowColor = */ android.graphics.Color.argb(50, 0, 0, 0),
                    )
                }
            )
        }

        for (i in minWeight..maxWeight) {
            // when using cos, sin, tan angle needs to be in radians
            val angleInRadians = (i - initialWeight + currentAngle - 90) * (PI / 180F).toFloat()

            // Decide line type checking if each value is dividable by 100, 5 or none
            val lineType = when {
                i % 10 == 0 -> LineType.TenStep
                i % 5 == 0 -> LineType.FiveStep
                else -> LineType.Normal
            }

            val lineLength = when (lineType) {
                LineType.TenStep -> style.tenStepLineLength.toPx()
                LineType.FiveStep -> style.fiveStepLineLength.toPx()
                LineType.Normal -> style.normalLineLength.toPx()
            }

            val lineColor = when (lineType) {
                LineType.TenStep -> style.tenStepLineColor
                LineType.FiveStep -> style.fiveStepLineColor
                LineType.Normal -> style.normalLineColor
            }

            // Outer outerRadius - lineLength to represent a line where start is at bottom
            // Also add the circleCenter to make it point to the center of the circle
            val lineStart = Offset(
                x = (outerRadius - lineLength) * cos(angleInRadians) + circleCenter.x,
                y = (outerRadius - lineLength) * sin(angleInRadians) + circleCenter.y,
            )

            // Outer outerRadius to represent a line where end is at top
            // Also add the circleCenter to make it point to the center of the circle
            val lineEnd = Offset(
                x = (outerRadius) * cos(angleInRadians) + circleCenter.x,
                y = (outerRadius) * sin(angleInRadians) + circleCenter.y,
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )

            drawContext.canvas.nativeCanvas.apply {
                if (lineType == LineType.TenStep) {
                    // Because the number will be at the bottom of the line
                    // Use the outer radius minus the line, minus a space of 5 dp minus the text size
                    // to draw text from bottom to top
                    val newRadius = outerRadius - lineLength - 5.dp.toPx() - style.textSize.toPx()
                    val x = newRadius * cos(angleInRadians) + circleCenter.x
                    val y = newRadius * sin(angleInRadians) + circleCenter.y

                    withRotation(
                        degrees = (angleInRadians * (180F / PI)).toFloat() + 90,
                        pivotX = x,
                        pivotY = y,
                    ) {
                        drawText(
                            /* text = */ abs(i).toString(),
                            /* x = */ x,
                            /* y = */ y,
                            /* paint = */ Paint().apply {
                                textSize = style.textSize.toPx()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }
            }

            val middleTop = Offset(
                // Use the center of the circle
                x = circleCenter.x,
                // Use the center of the circle minus the inner radius to move up, minus the scale indicator length because this point will be at the top
                y = circleCenter.y - innerRadius - style.scaleIndicatorLength.toPx()
            )

            val bottomLeft = Offset(
                x = circleCenter.x - 8.dp.toPx(),
                y = circleCenter.y - innerRadius,
            )

            val bottomRight = Offset(
                x = circleCenter.x + 8.dp.toPx(),
                y = circleCenter.y - innerRadius,
            )

            drawPath(
                color = style.scaleIndicatorColor,
                path = Path().apply {
                    moveTo(
                        middleTop.x,
                        middleTop.y
                    )        // Move the current path position to middleTop
                    lineTo(
                        bottomLeft.x,
                        bottomLeft.y
                    )      // from the current position draw a line to bottomLeft
                    lineTo(
                        bottomRight.x,
                        bottomRight.y
                    )    // from bottomLeft draw a line to bottomRight
                    lineTo(
                        middleTop.x,
                        middleTop.y
                    )        // from bottomRight draw a line to middleTop
                }
            )
        }
    }
}

