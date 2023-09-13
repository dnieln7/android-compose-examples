package xyz.dnieln7.portfolio.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AuthenticationDTO(
    @Json(name = "successful")
    val successful: Boolean,
    @Json(name = "message")
    val message: String,
    @Json(name = "result")
    val token: String,
)
