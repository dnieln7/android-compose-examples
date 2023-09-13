package xyz.dnieln7.portfolio.framework.provider

import android.content.Context
import android.content.SharedPreferences
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider

class DefaultAuthPreferencesProvider(context: Context) : AuthPreferencesProvider {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        AUTH_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    override fun saveAuthToken(token: String) {
        sharedPreferences.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    override fun getAuthToken(): String? {
        return sharedPreferences.getString(AUTH_TOKEN_KEY, null)
    }

    override fun saveAuthExpiration(expiration: Long) {
        sharedPreferences.edit().putLong(AUTH_EXPIRATION_KEY, expiration).apply()
    }

    override fun getAuthExpiration(): Long {
        return sharedPreferences.getLong(AUTH_EXPIRATION_KEY, AUTH_EXPIRATION_DEFAULT)
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}

private const val AUTH_PREFERENCES_NAME = "auth_preferences"

private const val AUTH_TOKEN_KEY = "authentication_token"

private const val AUTH_EXPIRATION_KEY = "authentication_expiration"
private const val AUTH_EXPIRATION_DEFAULT = 0L
