package soy.gabimoreno.danielnolasco.di.walking

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import soy.gabimoreno.danielnolasco.data.database.WalkingDatabase
import soy.gabimoreno.danielnolasco.data.datasource.geocoding.GeocodingRemoteDataSource
import soy.gabimoreno.danielnolasco.data.datasource.locationevent.LocalLocationEventDataSource
import soy.gabimoreno.danielnolasco.data.datasource.walkingsession.LocalWalkingSessionDataSource
import soy.gabimoreno.danielnolasco.data.server.GeoCodingApiService
import soy.gabimoreno.danielnolasco.data.server.OpenStreetMapClient

@Module
@InstallIn(SingletonComponent::class)
object WalkingDataModule {

    @Provides
    @Singleton
    fun provideOpenStreetMapClient(): OpenStreetMapClient {
        return OpenStreetMapClient()
    }

    @Provides
    @Singleton
    fun provideGeoCodingApiService(openStreetMapClient: OpenStreetMapClient): GeoCodingApiService {
        return Retrofit.Builder()
            .baseUrl(OPEN_STREET_MAP_API_URL)
            .client(openStreetMapClient.okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GeoCodingApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWalkingDatabase(@ApplicationContext context: Context): WalkingDatabase {
        return WalkingDatabase.build(context)
    }

    @Provides
    @Singleton
    fun provideGeocodingRemoteDataSource(geoCodingApiService: GeoCodingApiService): GeocodingRemoteDataSource {
        return GeocodingRemoteDataSource(geoCodingApiService)
    }

    @Provides
    @Singleton
    fun provideLocalWalkingSessionDataSource(walkingDatabase: WalkingDatabase): LocalWalkingSessionDataSource {
        return LocalWalkingSessionDataSource(walkingDatabase.walkingSessionDbModelDao())
    }

    @Provides
    @Singleton
    fun provideLocalLocationEventDataSource(walkingDatabase: WalkingDatabase): LocalLocationEventDataSource {
        return LocalLocationEventDataSource(walkingDatabase.locationEventDbModelDao())
    }
}

private const val OPEN_STREET_MAP_API_URL = "https://nominatim.openstreetmap.org/"
