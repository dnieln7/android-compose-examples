package soy.gabimoreno.danielnolasco.framework.location

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import timber.log.Timber

@SuppressLint("MissingPermission")
class FusedLocationProvider(
    private val locationClient: FusedLocationProviderClient,
) : LocationCallback() {

    private var locationUpdatesEnabled = false

    private val _locationUpdates = MutableSharedFlow<UpdatedLocation>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val locationUpdates = _locationUpdates.asSharedFlow()

    fun startLocationUpdates() {
        if (!locationUpdatesEnabled) {
            locationUpdatesEnabled = true
            locationClient.requestLocationUpdates(locationRequest, this, Looper.getMainLooper())
        }
    }

    fun stopLocationUpdates() {
        locationUpdatesEnabled = false
        locationClient.removeLocationUpdates(this)
    }

    override fun onLocationAvailability(locationAvailability: LocationAvailability) {
        Timber.i("locationAvailability: $locationAvailability")
    }

    override fun onLocationResult(locationResult: LocationResult) {
        val location = locationResult.lastLocation

        if (location != null) {
            _locationUpdates.tryEmit(
                UpdatedLocation.NewLocation(location.latitude, location.longitude)
            )
        } else {
            _locationUpdates.tryEmit(UpdatedLocation.EmptyLocation)
        }
    }
}
