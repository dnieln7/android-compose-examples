package xyz.dnieln7.canvascourse.common.composable.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp


@Composable
fun CustomText() {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    ) {
        drawText(
            textMeasurer = textMeasurer,
            text = "Canvas Text",
            topLeft = Offset(
                x = 100F,
                y = 100F
            ),
            style = TextStyle.Default.copy(
                color = Color.Red,
                fontSize = 100F.toSp(),
            )
        )
    }
}