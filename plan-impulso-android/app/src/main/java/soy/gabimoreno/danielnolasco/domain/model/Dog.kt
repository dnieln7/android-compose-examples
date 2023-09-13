package soy.gabimoreno.danielnolasco.domain.model

data class Dog(
    val name: String,
    val barking: Int,
    val coatLength: Int,
    val drooling: Int,
    val energy: Int,
    val goodWithChildren: Int,
    val goodWithOtherDogs: Int,
    val goodWithStrangers: Int,
    val grooming: Int,
    val imageLink: String,
    val maxHeightFemale: Double,
    val maxHeightMale: Double,
    val maxLifeExpectancy: Double,
    val maxWeightFemale: Double,
    val maxWeightMale: Double,
    val minHeightFemale: Double,
    val minHeightMale: Double,
    val minLifeExpectancy: Double,
    val minWeightFemale: Double,
    val minWeightMale: Double,
    val playfulness: Int,
    val protectiveness: Int,
    val shedding: Int,
    val trainability: Int
)
