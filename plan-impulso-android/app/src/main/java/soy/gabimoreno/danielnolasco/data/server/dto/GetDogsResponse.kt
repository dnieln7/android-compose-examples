package soy.gabimoreno.danielnolasco.data.server.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetDogsResponse(
    @Json(name = "name")
    val name: String,
    @Json(name = "barking")
    val barking: Int,
    @Json(name = "coat_length")
    val coatLength: Int,
    @Json(name = "drooling")
    val drooling: Int,
    @Json(name = "energy")
    val energy: Int,
    @Json(name = "good_with_children")
    val goodWithChildren: Int,
    @Json(name = "good_with_other_dogs")
    val goodWithOtherDogs: Int,
    @Json(name = "good_with_strangers")
    val goodWithStrangers: Int,
    @Json(name = "grooming")
    val grooming: Int,
    @Json(name = "image_link")
    val imageLink: String,
    @Json(name = "max_height_female")
    val maxHeightFemale: Double,
    @Json(name = "max_height_male")
    val maxHeightMale: Double,
    @Json(name = "max_life_expectancy")
    val maxLifeExpectancy: Double,
    @Json(name = "max_weight_female")
    val maxWeightFemale: Double,
    @Json(name = "max_weight_male")
    val maxWeightMale: Double,
    @Json(name = "min_height_female")
    val minHeightFemale: Double,
    @Json(name = "min_height_male")
    val minHeightMale: Double,
    @Json(name = "min_life_expectancy")
    val minLifeExpectancy: Double,
    @Json(name = "min_weight_female")
    val minWeightFemale: Double,
    @Json(name = "min_weight_male")
    val minWeightMale: Double,
    @Json(name = "playfulness")
    val playfulness: Int,
    @Json(name = "protectiveness")
    val protectiveness: Int,
    @Json(name = "shedding")
    val shedding: Int,
    @Json(name = "trainability")
    val trainability: Int
)
