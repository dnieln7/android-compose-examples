package xyz.dnieln7.portfolio.data.remote.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "role")
    val role: String = DEFAULT_ROLE,
)

private const val DEFAULT_ROLE = "admin"
