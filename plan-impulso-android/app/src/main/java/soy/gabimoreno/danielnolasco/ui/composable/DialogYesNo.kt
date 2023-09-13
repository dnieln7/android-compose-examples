package soy.gabimoreno.danielnolasco.ui.composable

import androidx.annotation.StringRes
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@Composable
fun DialogYesNo(
    @StringRes title: Int,
    @StringRes message: Int,
    onYes: () -> Unit,
    onNo: () -> Unit = {},
    dismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = dismiss,
        title = { Text(text = stringResource(title)) },
        text = { Text(text = stringResource(message)) },
        confirmButton = {
            TextButton(
                onClick = {
                    dismiss()
                    onYes()
                },
                content = { Text(stringResource(R.string.yes)) }
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dismiss()
                    onNo()
                },
                content = { Text(stringResource(R.string.no)) }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DialogYesNoPreview() {
    DanielNolascoTheme {
        DialogYesNo(
            title = R.string.are_you_sure,
            message = R.string.exit_walking_session,
            onYes = {},
            onNo = {},
            dismiss = {},
        )
    }
}
