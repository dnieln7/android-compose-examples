package soy.gabimoreno.danielnolasco.framework.provider

import android.content.Context
import android.content.SharedPreferences
import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider

class AppPreferencesProvider(context: Context) : PreferencesProvider {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        APP_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    override fun saveDogsLastFetchTimeInMillis(timeInMillis: Long) {
        sharedPreferences.edit().putLong(DOGS_LAST_FETCH_TIME_KEY, timeInMillis).apply()
    }

    override fun getDogsLastFetchTimeInMillis(): Long {
        return sharedPreferences.getLong(DOGS_LAST_FETCH_TIME_KEY, DOGS_LAST_FETCH_TIME_DEFAULT)
    }

    override fun saveDogsLastOffset(offset: Int): Boolean {
        return sharedPreferences.edit().putInt(DOGS_LAST_FETCH_OFFSET_KEY, offset).commit()
    }

    override fun getDogsLastOffset(): Int {
        return sharedPreferences.getInt(DOGS_LAST_FETCH_OFFSET_KEY, DOGS_LAST_FETCH_OFFSET_DEFAULT)
    }
}

private const val APP_PREFERENCES_NAME = "daniel_nolasco_preferences"

private const val DOGS_LAST_FETCH_TIME_KEY = "dogs_last_fetch_time"
private const val DOGS_LAST_FETCH_TIME_DEFAULT = 0L

private const val DOGS_LAST_FETCH_OFFSET_KEY = "dogs_last_offset"
private const val DOGS_LAST_FETCH_OFFSET_DEFAULT = 0
