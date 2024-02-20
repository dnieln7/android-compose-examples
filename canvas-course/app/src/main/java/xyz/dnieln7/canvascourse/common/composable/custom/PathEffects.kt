package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun PathEffects() {
    val infiniteTransition = rememberInfiniteTransition(label = "PathEffects")

    val phase by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 500F,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10000,
                easing = LinearEasing
            )
        ),
        label = "PathEffectsPhase"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        val square = Path().apply {
            moveTo(100F, 100F)
            lineTo(100F, 400F)
            lineTo(400F, 400F)
            lineTo(400F, 100F)
            close()
        }

        val triangle = Path().apply {
            moveTo(100F, 500F)
            lineTo(100F, 800F)
            lineTo(400F, 700F)
            close()
        }

        val bullet = Path().apply {
            moveTo(100F, 900F)
            lineTo(100F, 1200F)
            lineTo(400F, 1200F)
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
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(
                        50F, // Line size
                        30F, // Gap size
                    ),
                    phase = phase // Offset at the beginning
                ),
            )
        )

        drawPath(
            path = triangle,
            color = Color.Blue,
            style = Stroke(
                width = 20F,
                pathEffect = PathEffect.cornerPathEffect(
                    radius = 100F // Replaces sharp angles between line segments with rounded angles
                )
            )
        )

        val oval = Path().apply {
            addOval(
                Rect(
                    offset = Offset.Zero,
                    size = Size(100F, 50F),
                )
            )
        }

        drawPath(
            path = bullet,
            color = Color.Green,
            style = Stroke(
                width = 20F,
                pathEffect = PathEffect.stampedPathEffect(
                    shape = oval, // Dash using the provided path for each dash
                    advance = 100F, // Gap size
                    phase = phase, // Offset at the beginning
                    style = StampedPathEffectStyle.Morph, // How to transform the shape at each position as it is stamped (especially on angles)
                )
            )
        )

        val square2 = Path().apply {
            moveTo(100F, 1400F)
            lineTo(100F, 1800F)
            lineTo(400F, 1600F)
            close()
        }

        drawPath(
            path = square2,
            color = Color.Blue,
            style = Stroke(
                width = 20F,
                // Combines path effects
                pathEffect = PathEffect.chainPathEffect(
                    outer = PathEffect.stampedPathEffect(
                        shape = oval,
                        advance = 100F,
                        phase = 0F,
                        style = StampedPathEffectStyle.Morph,
                    ),
                    inner = PathEffect.cornerPathEffect(
                        radius = 100F
                    )
                )
            )
        )
    }
}