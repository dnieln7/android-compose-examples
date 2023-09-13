package soy.gabimoreno.danielnolasco.framework.permissions

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

fun ComponentActivity.permissionsLauncher(
    onGranted: () -> Unit,
    onDenied: () -> Unit = {},
): ActivityResultLauncher<Array<String>> {
    return registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (areAllPermissionsGranted(permissions)) {
            onGranted()
        } else {
            onDenied()
        }
    }
}
