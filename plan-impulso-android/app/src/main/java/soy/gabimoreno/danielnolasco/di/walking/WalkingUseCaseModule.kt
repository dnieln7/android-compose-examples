package soy.gabimoreno.danielnolasco.di.walking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import java.util.*
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository
import soy.gabimoreno.danielnolasco.domain.usecase.*
import xyz.dnieln7.core.domain.provider.ResourceProvider

@Module
@InstallIn(ViewModelComponent::class)
object WalkingUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideFormatSecondsToChronometerUseCase(): FormatSecondsToChronometerUseCase {
        return FormatSecondsToChronometerUseCase(Locale.getDefault())
    }

    @Provides
    @ViewModelScoped
    fun provideFormatEpochMillisecondsToReadableDateUseCase(): FormatEpochMillisecondsToReadableDateUseCase {
        return FormatEpochMillisecondsToReadableDateUseCase(Locale.getDefault())
    }

    @Provides
    @ViewModelScoped
    fun provideFormatSecondsToReadableDurationUseCase(
        resourceProvider: ResourceProvider,
    ): FormatSecondsToReadableDurationUseCase {
        return FormatSecondsToReadableDurationUseCase(resourceProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideFixIncompleteLocationEventsUseCase(
        locationEventRepository: LocationEventRepository,
        processLocationUseCase: ProcessLocationUseCase,
    ): FixIncompleteLocationEventsUseCase {
        return FixIncompleteLocationEventsUseCase(locationEventRepository, processLocationUseCase)
    }
}
