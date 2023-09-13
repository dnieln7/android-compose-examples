package soy.gabimoreno.danielnolasco.framework.permissions

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun composablePermissionsLauncher(
    onGranted: () -> Unit,
    onDenied: () -> Unit = {},
): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (areAllPermissionsGranted(permissions)) {
                onGranted()
            } else {
                onDenied()
            }
        }
    )
}
