package soy.gabimoreno.danielnolasco.di.pets

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider
import soy.gabimoreno.danielnolasco.domain.usecase.GetDogApiServiceErrorUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.GetLevelIndicatorColorUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.GetLevelIndicatorLevelUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.RefreshDogsFromApiUseCase
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase

@Module
@InstallIn(ViewModelComponent::class)
object PetsUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetLevelIndicatorColorUseCase(): GetLevelIndicatorColorUseCase {
        return GetLevelIndicatorColorUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideGetLevelIndicatorLevelUseCase(): GetLevelIndicatorLevelUseCase {
        return GetLevelIndicatorLevelUseCase()
    }

    @Provides
    @ViewModelScoped
    fun provideGetDogApiServiceErrorUseCase(
        resourceProvider: ResourceProvider,
        getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
    ): GetDogApiServiceErrorUseCase {
        return GetDogApiServiceErrorUseCase(resourceProvider, getErrorFromThrowableUseCase)
    }

    @Provides
    @ViewModelScoped
    fun provideRefreshDogsFromApiUseCase(preferencesProvider: PreferencesProvider): RefreshDogsFromApiUseCase {
        return RefreshDogsFromApiUseCase(preferencesProvider)
    }
}
