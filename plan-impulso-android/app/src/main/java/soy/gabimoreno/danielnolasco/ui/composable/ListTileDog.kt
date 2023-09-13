package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import soy.gabimoreno.danielnolasco.domain.extensions.isPlayful
import soy.gabimoreno.danielnolasco.domain.model.Dog
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListTileDog(dog: Dog, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                modifier = Modifier.weight(LIST_TILE_IMAGE_WEIGHT),
                model = dog.imageLink,
                contentDescription = stringResource(R.string.dog_picture),
                error = painterResource(soy.gabimoreno.danielnolasco.R.drawable.ic_broken_image),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(LIST_TILE_CONTENT_WEIGHT),
                text = dog.name
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (dog.isPlayful) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = stringResource(id = R.string.playful),
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListTileDogPreview() {
    DanielNolascoTheme {
        ListTileDog(
            dog = Dog(
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
                playfulness = 3,
                protectiveness = 3,
                shedding = 3,
                trainability = 3,
            ),
            onClick = {}
        )
    }
}

private const val LIST_TILE_IMAGE_WEIGHT = 0.30f
private const val LIST_TILE_CONTENT_WEIGHT = 0.70f
