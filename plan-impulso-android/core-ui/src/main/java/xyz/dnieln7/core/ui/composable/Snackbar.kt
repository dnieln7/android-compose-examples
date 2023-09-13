package xyz.dnieln7.core.ui.composable

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SnackBar(
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    message: String,
    onDismissed: () -> Unit = {},
) {
    LaunchedEffect(message) {
        coroutineScope.launch {
            val result = scaffoldState.snackbarHostState.showSnackbar(message)

            if (result == SnackbarResult.Dismissed) {
                onDismissed()
            }
        }
    }
}
