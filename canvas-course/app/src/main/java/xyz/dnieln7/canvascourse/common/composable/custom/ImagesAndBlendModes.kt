package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import xyz.dnieln7.canvascourse.R

@Composable
fun ImagesAndBlendModes() {
    val image = ImageBitmap.imageResource(id = R.drawable.astolfo)

    val aspectRatio = image.width.toFloat() / image.height.toFloat()

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawImage(
            image = image,
            dstOffset = IntOffset(x = 100, y = 100),
            dstSize = IntSize(
                width = (400F * aspectRatio).toInt(),
                height = 400,
            ),
//            colorFilter = ColorFilter.tint(Color.Blue), // Apply color to image
        )

        // BlendMode: How to combine pixels

        drawCircle(
            color = Color.Red,
            radius = 200F,
            center = Offset(300F, 300F),
            blendMode = BlendMode.Multiply,
        )
    }
}