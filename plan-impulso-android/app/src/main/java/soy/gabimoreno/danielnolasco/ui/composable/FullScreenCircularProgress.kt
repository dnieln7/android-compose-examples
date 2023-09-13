package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme

@Composable
fun FullScreenCircularProgress(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) { CircularProgressIndicator() }
}

@Preview(showBackground = true)
@Composable
fun FullScreenCircularProgressPreview() {
    DanielNolascoTheme {
        FullScreenCircularProgress()
    }
}
