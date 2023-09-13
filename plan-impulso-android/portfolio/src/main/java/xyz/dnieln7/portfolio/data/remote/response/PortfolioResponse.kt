package xyz.dnieln7.portfolio.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PortfolioResponse(
    @Json(name = "successful")
    val successful: Boolean,
    @Json(name = "message")
    val message: String,
)
