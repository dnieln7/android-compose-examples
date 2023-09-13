package xyz.dnieln7.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.framework.provider.AppResourceProvider

@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return AppResourceProvider(context)
    }
}
