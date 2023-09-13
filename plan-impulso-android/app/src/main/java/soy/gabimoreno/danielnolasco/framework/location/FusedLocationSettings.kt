package soy.gabimoreno.danielnolasco.framework.location

import arrow.core.Either
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.SettingsClient
import kotlinx.coroutines.tasks.await

class FusedLocationSettings(private val settingsClient: SettingsClient) {

    suspend fun checkLocationSettings(): Either<Throwable, Boolean> {
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)
            .build()

        val task = settingsClient.checkLocationSettings(locationSettingsRequest)

        return Either.catch { task.await().locationSettingsStates?.isLocationUsable ?: false }
    }
}
