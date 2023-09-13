package soy.gabimoreno.danielnolasco.framework.extensions

import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException

fun ResolvableApiException.buildIntentSenderRequest(): IntentSenderRequest {
    return IntentSenderRequest.Builder(resolution.intentSender).build()
}
