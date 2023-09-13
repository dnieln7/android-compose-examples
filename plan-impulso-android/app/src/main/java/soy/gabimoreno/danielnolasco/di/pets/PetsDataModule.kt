package soy.gabimoreno.danielnolasco.di.pets

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import soy.gabimoreno.danielnolasco.data.database.PetDatabase
import soy.gabimoreno.danielnolasco.data.datasource.dog.LocalDogDataSource
import soy.gabimoreno.danielnolasco.data.datasource.dog.RemoteDogDataSource
import soy.gabimoreno.danielnolasco.data.server.ApiNinjasClient
import soy.gabimoreno.danielnolasco.data.server.DogApiService
import soy.gabimoreno.danielnolasco.domain.provider.RemoteConfigProvider

@Module
@InstallIn(SingletonComponent::class)
object PetsDataModule {

    @Provides
    @Singleton
    fun provideApiNinjasClient(remoteConfigProvider: RemoteConfigProvider): ApiNinjasClient {
        return ApiNinjasClient(remoteConfigProvider)
    }

    @Provides
    @Singleton
    fun provideDogApi(apiNinjasClient: ApiNinjasClient): DogApiService {
        return Retrofit.Builder()
            .baseUrl(API_NINJAS_API_URL)
            .client(apiNinjasClient.okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(DogApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePetDatabase(@ApplicationContext context: Context): PetDatabase {
        return PetDatabase.build(context)
    }

    @Provides
    @Singleton
    fun provideRemoteDogDataSource(dogApiService: DogApiService): RemoteDogDataSource {
        return RemoteDogDataSource(dogApiService)
    }

    @Provides
    @Singleton
    fun provideLocalDogDataSource(petDatabase: PetDatabase): LocalDogDataSource {
        return LocalDogDataSource(petDatabase.dogDbModelDao())
    }
}

private const val API_NINJAS_API_URL = "https://api.api-ninjas.com/v1/"
