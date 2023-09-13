package soy.gabimoreno.danielnolasco.di.walking

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import soy.gabimoreno.danielnolasco.framework.location.FusedLocationProvider
import soy.gabimoreno.danielnolasco.framework.location.FusedLocationSettings

@Module
@InstallIn(SingletonComponent::class)
object LocationSingletonComponent {

    @Provides
    @Singleton
    fun provideLocationSettingsClient(@ApplicationContext context: Context): SettingsClient {
        return LocationServices.getSettingsClient(context)
    }

    @Provides
    @Singleton
    fun provideFusedLocationSettings(settingsClient: SettingsClient): FusedLocationSettings {
        return FusedLocationSettings(settingsClient)
    }
}

@Module
@InstallIn(ServiceComponent::class)
object LocationServiceComponent {

    @Provides
    @ServiceScoped
    fun provideLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @ServiceScoped
    fun provideLocationProvider(locationClient: FusedLocationProviderClient): FusedLocationProvider {
        return FusedLocationProvider(locationClient)
    }
}
