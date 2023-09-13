package soy.gabimoreno.danielnolasco.framework.permissions

import android.Manifest
import android.os.Build

val postNotificationsPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        emptyArray()
    }

val locationPermissions: Array<String>
    get() = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )
