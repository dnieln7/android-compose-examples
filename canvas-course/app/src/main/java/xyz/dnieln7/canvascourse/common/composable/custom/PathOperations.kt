package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun PathOperations() {
    // PathOperation.Difference: Returns everything that is in the path 1 but not in the path 2
    // PathOperation.ReverseDifference: Returns everything that is in the path 2 but not in the path 1
    // PathOperation.Union: Merges both paths
    // PathOperation.Intersect: Returns everything that is in the path 1 and also in the path 2 (the part were both paths overlap)
    // PathOperation.Xor: Returns everything except the part were both paths overlap
    val pathOperation = PathOperation.Intersect

    Canvas(modifier = Modifier.fillMaxSize()) {
        val square = Path().apply {
            addRect(
                Rect(
                    offset = Offset(400F, 400F), // TopLeft
                    size = Size(500F, 500F),
                )
            )
        }

        val circle = Path().apply {
            addOval(
                Rect(
                    center = Offset(400F, 400F),
                    radius = 250F,
                )
            )
        }

        val pathAfterOperation = Path().apply {
            op(square, circle, pathOperation)
        }

        drawPath(
            path = square,
            color = Color.Black,
            style = Stroke(width = 20F),
        )

        drawPath(
            path = circle,
            color = Color.Black,
            style = Stroke(width = 20F),
        )

        drawPath(
            path = pathAfterOperation,
            color = Color.Yellow,
        )
    }
}