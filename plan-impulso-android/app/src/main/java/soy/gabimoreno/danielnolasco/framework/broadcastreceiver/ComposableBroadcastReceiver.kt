package soy.gabimoreno.danielnolasco.framework.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext

@Composable
fun ComposableBroadcastReceiver(
    action: String,
    onReceive: (intent: Intent) -> Unit
) {
    val context = LocalContext.current
    val currentOnReceive by rememberUpdatedState(onReceive)

    DisposableEffect(context, action) {
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                intent?.let { currentOnReceive(it) }
            }
        }

        context.registerReceiver(broadcastReceiver, IntentFilter(action))

        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }
}
