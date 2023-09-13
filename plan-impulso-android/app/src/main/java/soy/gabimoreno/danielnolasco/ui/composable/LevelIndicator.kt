package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme

@Composable
fun LevelIndicator(
    level: Float,
    getLevelIndicatorColor: (Float) -> Color
) {
    LinearProgressIndicator(
        modifier = Modifier.height(8.dp),
        progress = level,
        color = getLevelIndicatorColor(level),
        backgroundColor = Color.Transparent
    )
}

@Preview(showBackground = true)
@Composable
fun LevelIndicatorPreview() {
    DanielNolascoTheme {
        LevelIndicator(level = 0.80f, getLevelIndicatorColor = { Color.Red })
    }
}
