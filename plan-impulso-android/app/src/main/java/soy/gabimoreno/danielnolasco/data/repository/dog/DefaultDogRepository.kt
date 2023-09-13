package soy.gabimoreno.danielnolasco.data.repository.dog

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import arrow.core.Either
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import soy.gabimoreno.danielnolasco.data.datasource.dog.LocalDogDataSource
import soy.gabimoreno.danielnolasco.data.datasource.dog.RemoteDogDataSource
import soy.gabimoreno.danielnolasco.data.mapper.toDomain
import soy.gabimoreno.danielnolasco.data.paging.DOGS_PAGE_SIZE
import soy.gabimoreno.danielnolasco.data.paging.DogRemoteMediator
import soy.gabimoreno.danielnolasco.domain.model.Dog
import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider
import soy.gabimoreno.danielnolasco.domain.repository.dog.DogRepository
import soy.gabimoreno.danielnolasco.domain.usecase.RefreshDogsFromApiUseCase

class DefaultDogRepository(
    private val remoteDogDataSource: RemoteDogDataSource,
    private val localDogDataSource: LocalDogDataSource,
    private val preferencesProvider: PreferencesProvider,
    private val refreshDogsFromApiUseCase: RefreshDogsFromApiUseCase
) : DogRepository {

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    override fun getDogs(): Flow<PagingData<Dog>> {
        return Pager(
            config = PagingConfig(
                pageSize = DOGS_PAGE_SIZE,
            ),
            remoteMediator = DogRemoteMediator(
                remoteDogDataSource = remoteDogDataSource,
                localDogDataSource = localDogDataSource,
                preferencesProvider = preferencesProvider,
                refreshDogsFromApiUseCase = refreshDogsFromApiUseCase,
            ),
            pagingSourceFactory = { localDogDataSource.observeDogs() }
        ).flow.mapLatest { data -> data.map { it.toDomain() } }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getLocalDogs(): Flow<PagingData<Dog>> {
        return Pager(
            config = PagingConfig(
                pageSize = DOGS_PAGE_SIZE,
            ),
            pagingSourceFactory = { localDogDataSource.observeDogs() }
        ).flow.mapLatest { data -> data.map { it.toDomain() } }
    }

    override suspend fun getDogByName(name: String): Either<Throwable, Dog?> {
        return Either.catch { localDogDataSource.getDogByName(name) }
    }

    override suspend fun deleteLocalDogs() {
        localDogDataSource.deleteDogs()
    }
}
