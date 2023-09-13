package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@Composable
fun FullScreenError(
    modifier: Modifier = Modifier,
    messageRes: Int?,
    message: String?,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(24.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (messageRes != null) {
                Text(text = stringResource(messageRes))
            } else {
                Text(text = "$message")
            }

            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onRetry) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FullScreenErrorPreview() {
    DanielNolascoTheme {
        FullScreenError(
            messageRes = null,
            message = "There was an error",
            onRetry = {}
        )
    }
}
