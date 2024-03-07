package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import xyz.dnieln7.canvascourse.R

@Composable
fun ImageReveal(
    modifier: Modifier = Modifier,
    coverColor: Color = Color.Black,
) {
    val image = ImageBitmap.imageResource(id = R.drawable.astolfo)
    val aspectRatio = image.width.toFloat() / image.height.toFloat()

    var widthDp by remember { mutableStateOf(0.dp) }
    var heightDp by remember { mutableStateOf(0.dp) }

    var position by remember { mutableStateOf(Offset.Zero) }

    Canvas(
        modifier = modifier
            .size(widthDp, heightDp)
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { position = it },
                    onDrag = { _, dragAmount ->
                        position += dragAmount
                    },
                )
            },
    ) {
        if (widthDp == 0.dp) {
            widthDp = if (size.width > image.width) {
                (size.width / density).dp
            } else {
                (image.width / density).dp
            }
        }

        if (heightDp == 0.dp) {
            heightDp = if (size.height > image.height) {
                (size.height / density).dp
            } else {
                (image.height / density).dp
            }
        }

        val imageSize = IntSize(
            width = (widthDp.toPx() * aspectRatio).toInt(),
            height = heightDp.toPx().toInt(),
        )

        drawImage(
            image = image,
            dstOffset = IntOffset(x = 0, y = center.y.toInt() - (heightDp.toPx() / 2).toInt()),
            dstSize = imageSize,
        )

        val circle = Path().apply {
            addOval(
                Rect(
                    center = position,
                    radius = 200F
                )
            )
        }

        val cover = Path().apply {
            addRect(
                Rect(
                    offset = Offset(x = 0F, y = center.y - heightDp.toPx() / 2),
                    size = imageSize.toSize(),
                )
            )
        }

        drawPath(
            path = circle,
            color = Color.White,
            blendMode = BlendMode.DstIn
        )

        clipPath(circle, ClipOp.Difference) {
            drawPath(
                path = cover,
                color = coverColor,
//                blendMode = BlendMode.Color
            )
        }
    }
}