package xyz.dnieln7.core.ui.composable

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextTitlePrimary(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.subtitle1,
        color = MaterialTheme.colors.primary,
    )
}

@Preview(showBackground = true)
@Composable
fun TextTitlePrimaryPreview() {
    MaterialTheme {
        TextTitlePrimary(text = "Hello!")
    }
}
