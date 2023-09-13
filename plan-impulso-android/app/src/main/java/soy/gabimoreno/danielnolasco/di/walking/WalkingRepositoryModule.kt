package soy.gabimoreno.danielnolasco.di.walking

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import soy.gabimoreno.danielnolasco.data.datasource.geocoding.GeocodingRemoteDataSource
import soy.gabimoreno.danielnolasco.data.repository.geocoding.DefaultGeocodingRepository
import soy.gabimoreno.danielnolasco.domain.repository.geocoding.GeocodingRepository

@Module
@InstallIn(SingletonComponent::class)
object WalkingRepositoryModule {

    @Provides
    @Singleton
    fun provideGeocodingRepository(geocodingRemoteDataSource: GeocodingRemoteDataSource): GeocodingRepository {
        return DefaultGeocodingRepository(geocodingRemoteDataSource)
    }
}
