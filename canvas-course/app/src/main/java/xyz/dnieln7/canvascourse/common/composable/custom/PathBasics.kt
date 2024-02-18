package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun PathBasics() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val square = Path().apply {
            moveTo(100F, 100F) // Path default position: 0,0
            lineTo(100F, 400F) // Line down
            lineTo(400F, 400F) // Line to right
            lineTo(400F, 100F) // Line up
            close() // line to the first position, equivalent to: lineTo(100F, 100F) // Line to left
        }

        val triangle = Path().apply {
            moveTo(100F, 500F)
            lineTo(100F, 800F)

            // x1,y1 = control point
            // x2,y2 = point to connect to (equal to lineTo())
            quadraticBezierTo(
                x1 = 1000F,
                y1 = 650F,
                x2 = 100F,
                y2 = 500F,
            )
        }

        val bullet = Path().apply {
            moveTo(100F, 900F)
            lineTo(100F, 1200F)
            lineTo(400F, 1200F)
            // x1,y1 = control point 2 (From top to bottom)
            // x2,y2 = control point 1 (From top to bottom)
            // x3,y3 = point to connect to (equal to lineTo())
            cubicTo(
                x1 = 600F,
                y1 = 1200F,
                x2 = 600F,
                y2 = 900F,
                x3 = 400F,
                y3 = 900F,
            )
            close()
        }

        drawPath(
            path = square,
            color = Color.Red,
            style = Stroke(
                width = 20F,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round, // Affects the union of two lines
            )
        )

        drawPath(
            path = triangle,
            color = Color.Blue,
            style = Stroke(
                width = 20F,
                cap = StrokeCap.Square,
                join = StrokeJoin.Miter, // Affects the union of two lines
                miter = 100F, // Control how much to cut off when an angle it's very sharp, must have StrokeJoin.Miter
            )
        )

        drawPath(
            path = bullet,
            color = Color.Green,
            style = Stroke(
                width = 20F,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            )
        )
    }
}