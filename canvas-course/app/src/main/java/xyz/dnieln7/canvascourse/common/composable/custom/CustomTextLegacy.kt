package xyz.dnieln7.canvascourse.common.composable.custom

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun CustomTextLegacy() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                /* text = */ "Canvas text",
                /* x = */ 100F,
                /* y = */ 100F,
                /* paint = */ Paint().apply {
                    color = Color.RED
                    textSize = 100F
                }
            )
        }
    }
}
