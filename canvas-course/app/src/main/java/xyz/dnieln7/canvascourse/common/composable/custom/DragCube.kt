package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun DragCube() {
    BoxWithConstraints {
        val width = 100.dp
        val height = 100.dp

        val minX = 0F
        val maxX = constraints.maxWidth.toFloat()
        val minY = 0F
        val maxY = constraints.maxHeight.toFloat()

        var position by remember { mutableStateOf(Offset.Zero) }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true) {
                    detectDragGestures { change, dragAmount ->
                        val topLeft = position
                        val topRight = Offset(
                            x = topLeft.x + width.toPx(),
                            y = topLeft.y,
                        )
                        val bottomLeft = Offset(
                            x = topLeft.x,
                            y = topLeft.y + height.toPx(),
                        )

                        if (change.position.x in topLeft.x..topRight.x && change.position.y in topLeft.y..bottomLeft.y) {
                            position = Offset(
                                x = (position.x + dragAmount.x).coerceIn(
                                    minimumValue = minX,
                                    maximumValue = maxX - width.toPx(),
                                ),
                                y = (position.y + dragAmount.y).coerceIn(
                                    minimumValue = minY,
                                    maximumValue = maxY - height.toPx(),
                                ),
                            )
                        }
                    }
                },
        ) {
            val size = Size(
                width = width.toPx(),
                height = height.toPx(),
            )

            if (position == Offset.Zero) {
                position = Offset(
                    x = center.x - (size.width / 2),
                    y = center.y - (size.height / 2),
                )
            }

            drawRect(
                color = Color.Red,
                topLeft = position,
                size = size
            )
        }
    }
}