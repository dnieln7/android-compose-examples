package soy.gabimoreno.danielnolasco.data.mapper

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import soy.gabimoreno.danielnolasco.fake.buildDog
import soy.gabimoreno.danielnolasco.fake.buildDogDbModel
import soy.gabimoreno.danielnolasco.fake.buildGetDogsResponse

class DogMappersTest {

    @Test
    fun `GIVEN a Dog WHEN toDbModel THEN get the expected DogDbModel`() {
        val dog = buildDog()
        val result = dog.toDbModel()

        result.name shouldBeEqualTo dog.name
        result.barking shouldBeEqualTo dog.barking
        result.coatLength shouldBeEqualTo dog.coatLength
        result.drooling shouldBeEqualTo dog.drooling
        result.energy shouldBeEqualTo dog.energy
        result.goodWithChildren shouldBeEqualTo dog.goodWithChildren
        result.goodWithOtherDogs shouldBeEqualTo dog.goodWithOtherDogs
        result.goodWithStrangers shouldBeEqualTo dog.goodWithStrangers
        result.grooming shouldBeEqualTo dog.grooming
        result.imageLink shouldBeEqualTo dog.imageLink
        result.maxHeightFemale shouldBeEqualTo dog.maxHeightFemale
        result.maxHeightMale shouldBeEqualTo dog.maxHeightMale
        result.maxLifeExpectancy shouldBeEqualTo dog.maxLifeExpectancy
        result.maxWeightFemale shouldBeEqualTo dog.maxWeightFemale
        result.maxWeightMale shouldBeEqualTo dog.maxWeightMale
        result.minHeightFemale shouldBeEqualTo dog.minHeightFemale
        result.minHeightMale shouldBeEqualTo dog.minHeightMale
        result.minLifeExpectancy shouldBeEqualTo dog.minLifeExpectancy
        result.minWeightFemale shouldBeEqualTo dog.minWeightFemale
        result.minWeightMale shouldBeEqualTo dog.minWeightMale
        result.playfulness shouldBeEqualTo dog.playfulness
        result.protectiveness shouldBeEqualTo dog.protectiveness
        result.shedding shouldBeEqualTo dog.shedding
        result.trainability shouldBeEqualTo dog.trainability
    }

    @Test
    fun `GIVEN a GetDogsResponse WHEN toDomain THEN get the expected Dog`() {
        val getDogsResponse = buildGetDogsResponse()
        val result = getDogsResponse.toDomain()

        result.name shouldBeEqualTo getDogsResponse.name
        result.barking shouldBeEqualTo getDogsResponse.barking
        result.coatLength shouldBeEqualTo getDogsResponse.coatLength
        result.drooling shouldBeEqualTo getDogsResponse.drooling
        result.energy shouldBeEqualTo getDogsResponse.energy
        result.goodWithChildren shouldBeEqualTo getDogsResponse.goodWithChildren
        result.goodWithOtherDogs shouldBeEqualTo getDogsResponse.goodWithOtherDogs
        result.goodWithStrangers shouldBeEqualTo getDogsResponse.goodWithStrangers
        result.grooming shouldBeEqualTo getDogsResponse.grooming
        result.imageLink shouldBeEqualTo getDogsResponse.imageLink
        result.maxHeightFemale shouldBeEqualTo getDogsResponse.maxHeightFemale
        result.maxHeightMale shouldBeEqualTo getDogsResponse.maxHeightMale
        result.maxLifeExpectancy shouldBeEqualTo getDogsResponse.maxLifeExpectancy
        result.maxWeightFemale shouldBeEqualTo getDogsResponse.maxWeightFemale
        result.maxWeightMale shouldBeEqualTo getDogsResponse.maxWeightMale
        result.minHeightFemale shouldBeEqualTo getDogsResponse.minHeightFemale
        result.minHeightMale shouldBeEqualTo getDogsResponse.minHeightMale
        result.minLifeExpectancy shouldBeEqualTo getDogsResponse.minLifeExpectancy
        result.minWeightFemale shouldBeEqualTo getDogsResponse.minWeightFemale
        result.minWeightMale shouldBeEqualTo getDogsResponse.minWeightMale
        result.playfulness shouldBeEqualTo getDogsResponse.playfulness
        result.protectiveness shouldBeEqualTo getDogsResponse.protectiveness
        result.shedding shouldBeEqualTo getDogsResponse.shedding
        result.trainability shouldBeEqualTo getDogsResponse.trainability
    }

    @Test
    fun `GIVEN a DogDbModel WHEN toDomain THEN get the expected Dog`() {
        val dogDbModel = buildDogDbModel()
        val result = dogDbModel.toDomain()

        result.name shouldBeEqualTo dogDbModel.name
        result.barking shouldBeEqualTo dogDbModel.barking
        result.coatLength shouldBeEqualTo dogDbModel.coatLength
        result.drooling shouldBeEqualTo dogDbModel.drooling
        result.energy shouldBeEqualTo dogDbModel.energy
        result.goodWithChildren shouldBeEqualTo dogDbModel.goodWithChildren
        result.goodWithOtherDogs shouldBeEqualTo dogDbModel.goodWithOtherDogs
        result.goodWithStrangers shouldBeEqualTo dogDbModel.goodWithStrangers
        result.grooming shouldBeEqualTo dogDbModel.grooming
        result.imageLink shouldBeEqualTo dogDbModel.imageLink
        result.maxHeightFemale shouldBeEqualTo dogDbModel.maxHeightFemale
        result.maxHeightMale shouldBeEqualTo dogDbModel.maxHeightMale
        result.maxLifeExpectancy shouldBeEqualTo dogDbModel.maxLifeExpectancy
        result.maxWeightFemale shouldBeEqualTo dogDbModel.maxWeightFemale
        result.maxWeightMale shouldBeEqualTo dogDbModel.maxWeightMale
        result.minHeightFemale shouldBeEqualTo dogDbModel.minHeightFemale
        result.minHeightMale shouldBeEqualTo dogDbModel.minHeightMale
        result.minLifeExpectancy shouldBeEqualTo dogDbModel.minLifeExpectancy
        result.minWeightFemale shouldBeEqualTo dogDbModel.minWeightFemale
        result.minWeightMale shouldBeEqualTo dogDbModel.minWeightMale
        result.playfulness shouldBeEqualTo dogDbModel.playfulness
        result.protectiveness shouldBeEqualTo dogDbModel.protectiveness
        result.shedding shouldBeEqualTo dogDbModel.shedding
        result.trainability shouldBeEqualTo dogDbModel.trainability
    }
}
