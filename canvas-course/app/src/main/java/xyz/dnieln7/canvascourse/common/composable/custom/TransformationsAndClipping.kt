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
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate

@Composable
fun TransformationsAndClipping() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        rotate(
            degrees = 45F,
            pivot = Offset(200F, 100F),
        ) {
            drawRect(
                color = Color.Red,
                topLeft = Offset(200F, 100F),
                size = Size(200F, 200F)
            )
        }
        translate(left = 300F) {
            drawRect(
                color = Color.Green,
                topLeft = Offset(100F, 100F),
                size = Size(200F, 200F)
            )
        }
        scale(
            scale = 0.5F,
            pivot = Offset(700F, 100F),
        ) {
            drawRect(
                color = Color.Blue,
                topLeft = Offset(700F, 100F),
                size = Size(200F, 200F)
            )
        }

        val circle = Path().apply {
            addOval(
                Rect(center = center, radius = 200F)
            )
        }

        drawPath(
            path = circle,
            color = Color.Black,
        )

        // Overlap the drawScope content inside the given path, without going out of the given path bounds
        clipPath(
            path = circle
        ) {
            drawRect(
                color = Color.Yellow,
                topLeft = center,
                size = Size(400F, 400F)
            )
        }
    }
}