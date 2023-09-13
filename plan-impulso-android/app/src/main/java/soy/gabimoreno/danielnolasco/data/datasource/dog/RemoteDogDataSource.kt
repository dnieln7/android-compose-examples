package soy.gabimoreno.danielnolasco.data.datasource.dog

import arrow.core.Either
import soy.gabimoreno.danielnolasco.data.mapper.toDomain
import soy.gabimoreno.danielnolasco.data.server.DogApiService
import soy.gabimoreno.danielnolasco.domain.model.Dog

class RemoteDogDataSource(private val dogApiService: DogApiService) {

    suspend fun getDogs(offset: Int): Either<Throwable, List<Dog>> {
        return Either.catch {
            dogApiService.getDogs(offset).map { it.toDomain() }
        }
    }
}
