package soy.gabimoreno.danielnolasco.data.server.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetReverseLocationResponse(
    @Json(name = "name")
    val name: String,
    @Json(name = "display_name")
    val displayName: String,
)
