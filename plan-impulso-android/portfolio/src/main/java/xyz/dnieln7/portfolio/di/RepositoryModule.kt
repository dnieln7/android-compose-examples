package xyz.dnieln7.portfolio.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.data.datasource.LocalProjectDataSource
import xyz.dnieln7.portfolio.data.datasource.RemoteAuthDataSource
import xyz.dnieln7.portfolio.data.datasource.RemoteProjectDataSource
import xyz.dnieln7.portfolio.data.repository.DefaultAuthRepository
import xyz.dnieln7.portfolio.data.repository.DefaultProjectRepository
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider
import xyz.dnieln7.portfolio.domain.repository.AuthRepository
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProjectRepository(
        dateProvider: DateProvider,
        remoteProjectDataSource: RemoteProjectDataSource,
        localProjectDataSource: LocalProjectDataSource,
    ): ProjectRepository {
        return DefaultProjectRepository(
            dateProvider,
            remoteProjectDataSource,
            localProjectDataSource
        )
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteAuthDataSource: RemoteAuthDataSource,
        authPreferencesProvider: AuthPreferencesProvider,
        dateProvider: DateProvider,
    ): AuthRepository {
        return DefaultAuthRepository(remoteAuthDataSource, authPreferencesProvider, dateProvider)
    }
}
