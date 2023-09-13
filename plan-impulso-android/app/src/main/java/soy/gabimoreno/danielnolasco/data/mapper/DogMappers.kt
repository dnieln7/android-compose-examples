package soy.gabimoreno.danielnolasco.data.mapper

import soy.gabimoreno.danielnolasco.data.database.model.DogDbModel
import soy.gabimoreno.danielnolasco.data.server.dto.GetDogsResponse
import soy.gabimoreno.danielnolasco.domain.model.Dog

fun Dog.toDbModel(): DogDbModel {
    return DogDbModel(
        name = name,
        barking = barking,
        coatLength = coatLength,
        drooling = drooling,
        energy = energy,
        goodWithChildren = goodWithChildren,
        goodWithOtherDogs = goodWithOtherDogs,
        goodWithStrangers = goodWithStrangers,
        grooming = grooming,
        imageLink = imageLink,
        maxHeightFemale = maxHeightFemale,
        maxHeightMale = maxHeightMale,
        maxLifeExpectancy = maxLifeExpectancy,
        maxWeightFemale = maxWeightFemale,
        maxWeightMale = maxWeightMale,
        minHeightFemale = minHeightFemale,
        minHeightMale = minHeightMale,
        minLifeExpectancy = minLifeExpectancy,
        minWeightFemale = minWeightFemale,
        minWeightMale = minWeightMale,
        playfulness = playfulness,
        protectiveness = protectiveness,
        shedding = shedding,
        trainability = trainability,
    )
}

fun GetDogsResponse.toDomain(): Dog {
    return Dog(
        name = name,
        barking = barking,
        coatLength = coatLength,
        drooling = drooling,
        energy = energy,
        goodWithChildren = goodWithChildren,
        goodWithOtherDogs = goodWithOtherDogs,
        goodWithStrangers = goodWithStrangers,
        grooming = grooming,
        imageLink = imageLink,
        maxHeightFemale = maxHeightFemale,
        maxHeightMale = maxHeightMale,
        maxLifeExpectancy = maxLifeExpectancy,
        maxWeightFemale = maxWeightFemale,
        maxWeightMale = maxWeightMale,
        minHeightFemale = minHeightFemale,
        minHeightMale = minHeightMale,
        minLifeExpectancy = minLifeExpectancy,
        minWeightFemale = minWeightFemale,
        minWeightMale = minWeightMale,
        playfulness = playfulness,
        protectiveness = protectiveness,
        shedding = shedding,
        trainability = trainability,
    )
}

fun DogDbModel.toDomain(): Dog {
    return Dog(
        name = name,
        barking = barking,
        coatLength = coatLength,
        drooling = drooling,
        energy = energy,
        goodWithChildren = goodWithChildren,
        goodWithOtherDogs = goodWithOtherDogs,
        goodWithStrangers = goodWithStrangers,
        grooming = grooming,
        imageLink = imageLink,
        maxHeightFemale = maxHeightFemale,
        maxHeightMale = maxHeightMale,
        maxLifeExpectancy = maxLifeExpectancy,
        maxWeightFemale = maxWeightFemale,
        maxWeightMale = maxWeightMale,
        minHeightFemale = minHeightFemale,
        minHeightMale = minHeightMale,
        minLifeExpectancy = minLifeExpectancy,
        minWeightFemale = minWeightFemale,
        minWeightMale = minWeightMale,
        playfulness = playfulness,
        protectiveness = protectiveness,
        shedding = shedding,
        trainability = trainability,
    )
}
