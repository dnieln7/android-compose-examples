package xyz.dnieln7.portfolio.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider
import xyz.dnieln7.portfolio.framework.provider.DefaultAuthPreferencesProvider

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideAuthPreferencesProvider(@ApplicationContext context: Context): AuthPreferencesProvider {
        return DefaultAuthPreferencesProvider(context)
    }
}
