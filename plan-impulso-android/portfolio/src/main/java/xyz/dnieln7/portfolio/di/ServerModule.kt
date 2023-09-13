package xyz.dnieln7.portfolio.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import xyz.dnieln7.portfolio.data.datasource.RemoteAuthDataSource
import xyz.dnieln7.portfolio.data.datasource.RemoteProjectDataSource
import xyz.dnieln7.portfolio.data.remote.PortfolioApiService
import xyz.dnieln7.portfolio.data.remote.PortfolioClient
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider

@Module
@InstallIn(SingletonComponent::class)
object ServerModule {

    @Provides
    @Singleton
    fun providePortfolioClient(authPreferencesProvider: AuthPreferencesProvider): PortfolioClient {
        return PortfolioClient(authPreferencesProvider)
    }

    @Provides
    @Singleton
    fun providePortfolioApiService(portfolioClient: PortfolioClient): PortfolioApiService {
        return Retrofit.Builder()
            .baseUrl(PORTFOLIO_API_URL)
            .client(portfolioClient.okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PortfolioApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteProjectDataSource(
        portfolioApiService: PortfolioApiService,
    ): RemoteProjectDataSource {
        return RemoteProjectDataSource(portfolioApiService)
    }

    @Provides
    @Singleton
    fun provideRemoteAuthDataSource(
        portfolioApiService: PortfolioApiService,
    ): RemoteAuthDataSource {
        return RemoteAuthDataSource(portfolioApiService)
    }
}

private const val PORTFOLIO_API_URL = "https://io.dnieln7.xyz/api/portfolio/"
