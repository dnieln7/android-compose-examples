package xyz.dnieln7.portfolio.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import xyz.dnieln7.portfolio.data.datasource.LocalProjectDataSource
import xyz.dnieln7.portfolio.data.local.PortfolioDatabase

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePortfolioDatabase(@ApplicationContext context: Context): PortfolioDatabase {
        return PortfolioDatabase.build(context)
    }

    @Provides
    @Singleton
    fun provideLocalProjectsDataSource(portfolioDatabase: PortfolioDatabase): LocalProjectDataSource {
        return LocalProjectDataSource(portfolioDatabase.projectDbModelDao())
    }
}
