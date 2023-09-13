package soy.gabimoreno.danielnolasco.fake

import soy.gabimoreno.danielnolasco.data.database.model.DogDbModel
import soy.gabimoreno.danielnolasco.data.server.dto.GetDogsResponse
import soy.gabimoreno.danielnolasco.domain.model.Dog

fun buildDog(): Dog {
    return Dog(
        name = "Golden Retriever",
        barking = 2,
        coatLength = 3,
        drooling = 2,
        energy = 4,
        goodWithChildren = 5,
        goodWithOtherDogs = 5,
        goodWithStrangers = 4,
        grooming = 3,
        imageLink = "https://www.example.com/images/golden-retriever.jpg",
        maxHeightFemale = 22.5,
        maxHeightMale = 24.0,
        maxLifeExpectancy = 12.0,
        maxWeightFemale = 70.0,
        maxWeightMale = 75.0,
        minHeightFemale = 20.0,
        minHeightMale = 22.0,
        minLifeExpectancy = 10.0,
        minWeightFemale = 55.0,
        minWeightMale = 65.0,
        playfulness = 4,
        protectiveness = 3,
        shedding = 3,
        trainability = 5
    )
}

fun buildGetDogsResponse(): GetDogsResponse {
    return GetDogsResponse(
        name = "Golden Retriever",
        barking = 2,
        coatLength = 3,
        drooling = 2,
        energy = 4,
        goodWithChildren = 5,
        goodWithOtherDogs = 5,
        goodWithStrangers = 4,
        grooming = 3,
        imageLink = "https://www.example.com/images/golden-retriever.jpg",
        maxHeightFemale = 22.5,
        maxHeightMale = 24.0,
        maxLifeExpectancy = 12.0,
        maxWeightFemale = 70.0,
        maxWeightMale = 75.0,
        minHeightFemale = 20.0,
        minHeightMale = 22.0,
        minLifeExpectancy = 10.0,
        minWeightFemale = 55.0,
        minWeightMale = 65.0,
        playfulness = 4,
        protectiveness = 3,
        shedding = 3,
        trainability = 5
    )
}

fun buildDogDbModel(): DogDbModel {
    return DogDbModel(
        name = "Golden Retriever",
        barking = 2,
        coatLength = 3,
        drooling = 2,
        energy = 4,
        goodWithChildren = 5,
        goodWithOtherDogs = 5,
        goodWithStrangers = 4,
        grooming = 3,
        imageLink = "https://www.example.com/images/golden-retriever.jpg",
        maxHeightFemale = 22.5,
        maxHeightMale = 24.0,
        maxLifeExpectancy = 12.0,
        maxWeightFemale = 70.0,
        maxWeightMale = 75.0,
        minHeightFemale = 20.0,
        minHeightMale = 22.0,
        minLifeExpectancy = 10.0,
        minWeightFemale = 55.0,
        minWeightMale = 65.0,
        playfulness = 4,
        protectiveness = 3,
        shedding = 3,
        trainability = 5
    )
}
