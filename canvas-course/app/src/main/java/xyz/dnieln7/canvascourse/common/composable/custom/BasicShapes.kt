package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun BasicShapes() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(20.dp),
    ) {
        drawRect(
            color = Color.Green,
            size = size,
        )
        drawRect(
            color = Color.Black,
            topLeft = Offset(
                x = 10.dp.toPx(),
                y = 10.dp.toPx(),
            ),
            size = Size(
                width = 100.dp.toPx(),
                height = 100.dp.toPx(),
            ),
            style = Stroke(
                width = 1.dp.toPx(),
            ),
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.Red, Color.Yellow),
                center = center,
                radius = 25.dp.toPx(),
            ),
            radius = 25.dp.toPx(),
            center = Offset(50.dp.toPx(), 50.dp.toPx()),
        )
        drawArc(
            color = Color.Blue,
            startAngle = 0F,    // start degrees
            sweepAngle = 250F,  // end degrees
            useCenter = true,   // Should the start and end connect to the center or themselves?
            topLeft = Offset(
                x = 150.dp.toPx(),
                y = 50.dp.toPx(),
            ),
            size = Size(
                width = 100.dp.toPx(),
                height = 100.dp.toPx(),
            )
        )
        drawOval(
            color = Color.Magenta,
            topLeft = center,
            size = Size(
                width = 50.dp.toPx(),
                height = 100.dp.toPx(),
            )
        )
        drawLine(
            color = Color.Red,
            start = Offset(
                x = 0.dp.toPx(),
                y = 200.dp.toPx(),
            ),
            end = Offset(
                x = 300.dp.toPx(),
                y = 200.dp.toPx(),
            ),
            strokeWidth = 5.dp.toPx(),
        )
    }
}