package soy.gabimoreno.danielnolasco.data.datasource.dog

import androidx.paging.PagingSource
import soy.gabimoreno.danielnolasco.data.database.dao.DogDbModelDao
import soy.gabimoreno.danielnolasco.data.database.model.DogDbModel
import soy.gabimoreno.danielnolasco.data.mapper.toDbModel
import soy.gabimoreno.danielnolasco.data.mapper.toDomain
import soy.gabimoreno.danielnolasco.domain.model.Dog

class LocalDogDataSource(private val dogDbModelDao: DogDbModelDao) {

    suspend fun saveDogs(dogs: List<Dog>) {
        dogDbModelDao.insertDogDbModels(dogs.map { it.toDbModel() })
    }

    suspend fun getDogs(): List<Dog> {
        return dogDbModelDao.getDogDbModels().map { it.toDomain() }
    }

    fun observeDogs(): PagingSource<Int, DogDbModel> {
        return dogDbModelDao.observeDogDbModels()
    }

    suspend fun getDogByName(name: String): Dog? {
        return dogDbModelDao.getDogDbModelsByName(name = name)?.toDomain()
    }

    suspend fun deleteDogs() {
        dogDbModelDao.deleteDogDbModels()
    }

    suspend fun isEmpty(): Boolean {
        return dogDbModelDao.count() <= 0
    }
}
