package soy.gabimoreno.danielnolasco.di.walking

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import soy.gabimoreno.danielnolasco.data.datasource.locationevent.LocalLocationEventDataSource
import soy.gabimoreno.danielnolasco.data.datasource.walkingsession.LocalWalkingSessionDataSource
import soy.gabimoreno.danielnolasco.data.repository.locationevent.DefaultLocationEventRepository
import soy.gabimoreno.danielnolasco.data.repository.walkingsession.DefaultWalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.usecase.DeleteWalkingSessionAndRelatedDataUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.GetWalkingSessionsOlderThan7DaysUseCase

@Module
@InstallIn(SingletonComponent::class)
object WalkingSingletonModule {

    @Provides
    @Singleton
    fun provideWorkManagerConfiguration(workerFactory: HiltWorkerFactory): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context,
        configuration: Configuration,
    ): WorkManager {
        WorkManager.initialize(context, configuration)

        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideWalkingSessionRepository(
        localWalkingSessionDataSource: LocalWalkingSessionDataSource,
    ): WalkingSessionRepository {
        return DefaultWalkingSessionRepository(localWalkingSessionDataSource)
    }

    @Provides
    @Singleton
    fun provideLocationEventRepository(
        localLocationEventDataSource: LocalLocationEventDataSource,
    ): LocationEventRepository {
        return DefaultLocationEventRepository(localLocationEventDataSource)
    }

    @Provides
    @Singleton
    fun provideGetWalkingSessionsOlderThan7DaysUseCase(
        walkingSessionRepository: WalkingSessionRepository,
    ): GetWalkingSessionsOlderThan7DaysUseCase {
        return GetWalkingSessionsOlderThan7DaysUseCase(walkingSessionRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteWalkingSessionAndRelatedDataUseCase(
        walkingSessionRepository: WalkingSessionRepository,
        locationEventRepository: LocationEventRepository,
    ): DeleteWalkingSessionAndRelatedDataUseCase {
        return DeleteWalkingSessionAndRelatedDataUseCase(
            walkingSessionRepository,
            locationEventRepository,
        )
    }
}
