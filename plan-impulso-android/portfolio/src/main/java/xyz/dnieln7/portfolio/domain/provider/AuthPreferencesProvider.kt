package xyz.dnieln7.portfolio.domain.provider

interface AuthPreferencesProvider {
    fun saveAuthToken(token: String)
    fun getAuthToken(): String?
    fun saveAuthExpiration(expiration: Long)
    fun getAuthExpiration(): Long
    fun clear()
}
