package xyz.dnieln7.canvascourse.common.composable.custom

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextLegacy() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        drawContext.canvas.nativeCanvas.apply {
            drawText(
                /* text = */ "Legacy Canvas text",
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
