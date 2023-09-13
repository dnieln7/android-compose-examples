package soy.gabimoreno.danielnolasco.di.pets

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import soy.gabimoreno.danielnolasco.data.datasource.dog.LocalDogDataSource
import soy.gabimoreno.danielnolasco.data.datasource.dog.RemoteDogDataSource
import soy.gabimoreno.danielnolasco.data.repository.cat.DefaultCatInMemoryRepository
import soy.gabimoreno.danielnolasco.data.repository.dog.DefaultDogRepository
import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider
import soy.gabimoreno.danielnolasco.domain.repository.cat.CatRepository
import soy.gabimoreno.danielnolasco.domain.repository.dog.DogRepository
import soy.gabimoreno.danielnolasco.domain.usecase.RefreshDogsFromApiUseCase

@Module
@InstallIn(ViewModelComponent::class)
object PetsRepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideCatRepository(): CatRepository {
        return DefaultCatInMemoryRepository()
    }

    @Provides
    @ViewModelScoped
    fun provideDogRepository(
        remoteDogDataSource: RemoteDogDataSource,
        localDogDataSource: LocalDogDataSource,
        preferencesProvider: PreferencesProvider,
        refreshDogsFromApiUseCase: RefreshDogsFromApiUseCase,
    ): DogRepository {
        return DefaultDogRepository(
            remoteDogDataSource,
            localDogDataSource,
            preferencesProvider,
            refreshDogsFromApiUseCase,
        )
    }
}
