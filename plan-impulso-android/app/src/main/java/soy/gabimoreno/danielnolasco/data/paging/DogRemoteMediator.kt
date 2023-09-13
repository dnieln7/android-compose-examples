package soy.gabimoreno.danielnolasco.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import soy.gabimoreno.danielnolasco.data.database.model.DogDbModel
import soy.gabimoreno.danielnolasco.data.datasource.dog.LocalDogDataSource
import soy.gabimoreno.danielnolasco.data.datasource.dog.RemoteDogDataSource
import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider
import soy.gabimoreno.danielnolasco.domain.usecase.RefreshDogsFromApiUseCase

@OptIn(ExperimentalPagingApi::class)
class DogRemoteMediator(
    private val remoteDogDataSource: RemoteDogDataSource,
    private val localDogDataSource: LocalDogDataSource,
    private val preferencesProvider: PreferencesProvider,
    private val refreshDogsFromApiUseCase: RefreshDogsFromApiUseCase,
) : RemoteMediator<Int, DogDbModel>() {

    private var offset = 0

    override suspend fun initialize(): InitializeAction {
        val forceRefresh = refreshDogsFromApiUseCase(System.currentTimeMillis()) ||
            localDogDataSource.isEmpty()

        return if (forceRefresh) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DogDbModel>
    ): MediatorResult {
        offset = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                preferencesProvider.getDogsLastOffset().plus(DOGS_PAGE_SIZE)
            }
        }

        return remoteDogDataSource.getDogs(offset).fold(
            {
                MediatorResult.Error(it)
            },
            {
                if (loadType == LoadType.REFRESH) {
                    localDogDataSource.deleteDogs()
                }

                val result = if (it.isNotEmpty()) {
                    localDogDataSource.saveDogs(it)
                    MediatorResult.Success(false)
                } else {
                    MediatorResult.Success(true)
                }

                preferencesProvider.saveDogsLastOffset(offset)

                result
            }
        )
    }
}

const val DOGS_PAGE_SIZE = 20
