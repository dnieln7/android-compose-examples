package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun PathText() {
    Canvas(modifier = Modifier.fillMaxSize()) {

        val path = Path().apply {
            moveTo(100F, 100F)
            lineTo(100F, 800F)
            lineTo(800F, 800F)
            lineTo(800F, 100F)
            lineTo(400F, 100F)
        }

        drawContext.canvas.nativeCanvas.apply {
            drawTextOnPath(
                /* text = */ "This text is in the center of the path",
                /* path = */ path.asAndroidPath(),
                // Text is drawn in the middle of the path line, use horizontal and vertical offset to adjust
                /* hOffset = */ 30F,
                /* vOffset = */ 60F,
                /* paint = */ android.graphics.Paint().apply {
                    color = NativeColor.RED
                    textSize = 50F
                    textAlign = NativePaintAlign.CENTER
                }
            )
        }

        drawPath(
            path = path,
            color = Color.Black,
            style = Stroke(
                width = 20F,
            )
        )
    }
}