package soy.gabimoreno.danielnolasco.data.repository.cat

import soy.gabimoreno.danielnolasco.domain.model.Cat
import soy.gabimoreno.danielnolasco.domain.repository.cat.CatRepository

class DefaultCatInMemoryRepository : CatRepository {

    private val cats = listOf(
        Cat(
            name = "Abyssinian",
            origin = "Southeast Asia",
            playfulness = 5
        ),
        Cat(
            name = "Greece",
            origin = "Aegean",
            playfulness = 4
        ),
        Cat(
            name = "American Bobtail",
            origin = "United States and Canada",
            playfulness = 4
        ),
        Cat(
            name = "California, USA",
            origin = "American Curl",
            playfulness = 4
        ),
        Cat(
            name = "American Shorthair",
            origin = "United States",
            playfulness = 2
        ),
        Cat(
            name = "American Wirehair",
            origin = "United States",
            playfulness = 3
        ),
        Cat(
            name = "Aphrodite Giant",
            origin = "Cyprus",
            playfulness = 4
        ),
        Cat(
            name = "Arabian Mau",
            origin = "Saudi Arabia",
            playfulness = 4
        ),
    )

    override suspend fun getCats(): List<Cat> {
        return cats
    }

    override suspend fun getCatByName(name: String): Cat? {
        return cats.find { it.name == name }
    }
}
