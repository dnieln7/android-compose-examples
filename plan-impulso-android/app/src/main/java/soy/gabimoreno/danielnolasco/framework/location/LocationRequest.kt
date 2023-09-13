package soy.gabimoreno.danielnolasco.framework.location

import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.Priority

val locationRequest
    get() = LocationRequest
        .Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_UPDATES_INTERVAL)
        .setMinUpdateIntervalMillis(LOCATION_UPDATES_MIN_INTERVAL)
        .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        .build()

private const val LOCATION_UPDATES_INTERVAL = 10_000L
private const val LOCATION_UPDATES_MIN_INTERVAL = 5_000L
