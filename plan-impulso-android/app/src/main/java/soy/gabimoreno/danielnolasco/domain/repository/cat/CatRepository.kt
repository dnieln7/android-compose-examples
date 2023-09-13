package soy.gabimoreno.danielnolasco.domain.repository.cat

import soy.gabimoreno.danielnolasco.domain.model.Cat

interface CatRepository {

    suspend fun getCats(): List<Cat>

    suspend fun getCatByName(name: String): Cat?
}
