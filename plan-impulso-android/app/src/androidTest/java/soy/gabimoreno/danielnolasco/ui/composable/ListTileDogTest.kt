package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test
import soy.gabimoreno.danielnolasco.domain.model.Dog
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme

class ListTileDogTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun when_dog_playfulness_is_4_or_more_a_heart_icon_should_be_displayed() {
        val dog = buildDog(playfulness = 5)

        composeTestRule.setContent {
            DanielNolascoTheme {
                ListTileDog(dog = dog, onClick = {})
            }
        }

        composeTestRule.onNodeWithContentDescription("Playful").assertIsDisplayed()
    }

    @Test
    fun when_dog_playfulness_is_3_or_less_a_heart_icon_should_not_be_displayed() {
        val dog = buildDog(1)

        composeTestRule.setContent {
            DanielNolascoTheme {
                ListTileDog(dog = dog, onClick = {})
            }
        }

        composeTestRule.onAllNodesWithContentDescription("Playful").assertCountEquals(0)
    }

    private fun buildDog(playfulness: Int) = Dog(
        name = "Affenpinscher",
        barking = 3,
        coatLength = 2,
        drooling = 1,
        energy = 3,
        goodWithChildren = 3,
        goodWithOtherDogs = 3,
        goodWithStrangers = 5,
        grooming = 3,
        imageLink = "https://api-ninjas.com/images/dogs/affenpinscher.jpg",
        maxHeightFemale = 11.5,
        maxHeightMale = 11.5,
        maxLifeExpectancy = 15.0,
        maxWeightFemale = 10.0,
        maxWeightMale = 10.0,
        minHeightFemale = 9.0,
        minHeightMale = 9.0,
        minLifeExpectancy = 12.0,
        minWeightFemale = 9.0,
        minWeightMale = 9.0,
        playfulness = playfulness,
        protectiveness = 3,
        shedding = 3,
        trainability = 3,
    )
}
