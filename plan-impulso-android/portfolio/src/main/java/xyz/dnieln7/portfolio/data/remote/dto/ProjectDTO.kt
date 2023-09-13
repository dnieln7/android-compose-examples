package xyz.dnieln7.portfolio.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProjectDTO(
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "ownership")
    val ownership: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "year")
    val year: Int,
    @Json(name = "importance")
    val importance: Double,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "images")
    val images: List<String>,
    @Json(name = "tags")
    val tags: List<String>,
    @Json(name = "duration")
    val duration: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "features")
    val features: List<String>,
    @Json(name = "technologies")
    val technologies: List<String>,
    @Json(name = "androidGit")
    val androidGit: String,
    @Json(name = "androidUrl")
    val androidUrl: String,
    @Json(name = "webUrl")
    val webUrl: String,
    @Json(name = "webGit")
    val webGit: String,
    @Json(name = "programUrl")
    val programUrl: String,
    @Json(name = "programGit")
    val programGit: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "updatedAt")
    val updatedAt: String,
)
