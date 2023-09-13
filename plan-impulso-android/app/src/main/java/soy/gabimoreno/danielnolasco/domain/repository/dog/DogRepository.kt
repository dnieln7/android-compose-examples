package soy.gabimoreno.danielnolasco.domain.repository.dog

import androidx.paging.PagingData
import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import soy.gabimoreno.danielnolasco.domain.model.Dog

interface DogRepository {
    fun getDogs(): Flow<PagingData<Dog>>
    fun getLocalDogs(): Flow<PagingData<Dog>>
    suspend fun getDogByName(name: String): Either<Throwable, Dog?>
    suspend fun deleteLocalDogs()
}
