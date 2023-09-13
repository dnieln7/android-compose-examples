package soy.gabimoreno.danielnolasco.framework.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toastShort(@StringRes messageRes: Int) {
    Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(@StringRes messageRes: Int) {
    Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show()
}
