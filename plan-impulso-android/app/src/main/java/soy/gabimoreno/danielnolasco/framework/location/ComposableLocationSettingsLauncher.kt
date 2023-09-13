package soy.gabimoreno.danielnolasco.framework.location

import android.app.Activity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun composableLocationSettingsLauncher(
    onGranted: () -> Unit,
    onDenied: () -> Unit,
): ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = {
            if (it.resultCode == Activity.RESULT_OK) {
                onGranted()
            } else {
                onDenied()
            }
        }
    )
}
