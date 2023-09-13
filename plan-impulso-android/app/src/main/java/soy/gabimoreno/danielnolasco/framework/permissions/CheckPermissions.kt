package soy.gabimoreno.danielnolasco.framework.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
    val results = mutableMapOf<String, Boolean>()

    return if (permissions.isNotEmpty()) {
        permissions.forEach { permission ->
            results[permission] = ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        areAllPermissionsGranted(results)
    } else {
        true
    }
}

fun areAllPermissionsGranted(permissions: Map<String, Boolean>?): Boolean {
    return if (permissions.isNullOrEmpty()) {
        false
    } else {
        return permissions.values.all { it }
    }
}
