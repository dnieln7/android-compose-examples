package soy.gabimoreno.danielnolasco.ui.screen.walkingplayground

import com.google.android.gms.common.api.ResolvableApiException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent

data class WalkingPlaygroundState(
    val hasLocationCapabilities: Boolean = true,
    val locationCapabilitiesException: ResolvableApiException? = null,
    val isReady: Boolean = false,
    val isStarted: Boolean = false,
    val seconds: String = INITIAL_CHRONOMETER_STRING,
    val locationEvents: Flow<List<LocationEvent>> = emptyFlow(),
)

private const val INITIAL_CHRONOMETER_STRING = "00:00:00"
