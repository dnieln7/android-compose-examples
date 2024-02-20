package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun PathAnimations() {
    val pathLengthPercentage = remember { Animatable(0F) }

    LaunchedEffect(key1 = true) {
        pathLengthPercentage.animateTo(
            targetValue = 1F,
            animationSpec = tween(durationMillis = 5000),
        )
    }

    val triangle = Path().apply {
        moveTo(100F, 100F)
        lineTo(100F, 400F)
        lineTo(600F, 400F)
    }

    val animatedTriangle = Path()

    // Create a new path from a portion of another path
    PathMeasure().apply {
        setPath(
            path = triangle,
            forceClosed = true, // Apply close() a the end of the path
        )

        // Get a segment of the previous path
        // From 0 percent to (pathLengthPercentage * triangle path length)
        getSegment(
            startDistance = 0F,
            stopDistance = pathLengthPercentage.value * length,
            destination = animatedTriangle,
            startWithMoveTo = true, // Moves the animatedTriangle path start to the same coordinate triangle path starts
        )
    }

    val arrow = Path().apply {
        moveTo(100F, 500F)
        quadraticBezierTo(
            x1 = 400F,
            y1 = 500F,
            x2 = 400F,
            y2 = 1000F,
        )
    }

    // val animatedArrow = NativePath()
    // val positions = FloatArray(2) // Position of the ending part of the path
    // val tangents = FloatArray(2) // Tangent of the ending part of the path

    val animatedArrow = Path()
    val position: Offset // Position of the ending part of the path
    val tangent: Offset // Tangent of the ending part of the path

    PathMeasure().apply {
        setPath(
            path = arrow,
            forceClosed = false,
        )

        getSegment(
            startDistance = 0F,
            stopDistance = pathLengthPercentage.value * length,
            destination = animatedArrow,
            startWithMoveTo = true,
        )

        position = getPosition(pathLengthPercentage.value * length)
        tangent = getTangent(pathLengthPercentage.value * length)
    }

//    NativePathMeasure().apply {
//        setPath(
//            /* path = */ arrow.asAndroidPath(),
//            /* forceClosed = */ false,
//        )
//
//        getSegment(
//            /* startD = */ 0F,
//            /* stopD = */ pathLengthPercentage.value * length,
//            /* dst = */ animatedArrow,
//            /* startWithMoveTo = */ true,
//        )
//
//        // Get position and tangent of a given path
//        getPosTan(
//            /* distance = */ pathLengthPercentage.value * length,
//            /* pos = */ positions, // Result of the position
//            /* tan = */ tangents, // Result of the tangent values
//        )
//    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawPath(
            path = animatedTriangle,
            color = Color.Red,
            style = Stroke(
                width = 20F,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
            )
        )

        drawPath(
            path = animatedArrow,
            color = Color.Magenta,
            style = Stroke(
                width = 20F,
            )
        )

        val degrees = -atan2(tangent.x, tangent.y) * (180F / PI)

        rotate(
            degrees = degrees.toFloat() - 180F,
            pivot = position
        ) {
            drawPath(
                path = Path().apply {
                    // Move to the top part of the triangle
                    moveTo(
                        x = position.x,
                        y = position.y - 30F,
                    )
                    // Move to the bottom left part of the triangle
                    lineTo(
                        x = position.x - 30F,
                        y = position.y + 60F
                    )
                    // Move to the bottom right part of the triangle
                    lineTo(
                        x = position.x + 30F,
                        y = position.y + 60F,
                    )
                    // Close and connect the bottom right part with the top part of the triangle
                    close()
                },
                color = Color.Black,
            )
        }

//        drawPath(
//            path = animatedArrow.asComposePath(),
//            color = Color.LightGray,
//            style = Stroke(
//                width = 20F,
//            )
//        )
//
//        val degrees = -atan2(y = tangents[0], x = tangents[1]) * (180F / PI)
//
//        rotate(
//            degrees = degrees.toFloat() - 180F,
//            pivot = Offset(positions[0], positions[1])
//        ) {
//            drawPath(
//                path = Path().apply {
//                    // Move to the top part of the triangle
//                    moveTo(
//                        x = positions[0],
//                        y = positions[1] - 30F,
//                    )
//                    // Move to the bottom left part of the triangle
//                    lineTo(
//                        x = positions[0] - 30F,
//                        y = positions[1] + 60F
//                    )
//                    // Move to the bottom right part of the triangle
//                    lineTo(
//                        x = positions[0] + 30F,
//                        y = positions[1] + 60F,
//                    )
//                    // Close and connect the bottom right part with the top part of the triangle
//                    close()
//                },
//                color = Color.Gray,
//            )
//        }
    }
}