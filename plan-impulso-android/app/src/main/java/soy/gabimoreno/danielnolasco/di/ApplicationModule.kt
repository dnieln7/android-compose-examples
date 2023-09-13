package soy.gabimoreno.danielnolasco.di

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import soy.gabimoreno.danielnolasco.data.server.*
import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider
import soy.gabimoreno.danielnolasco.domain.provider.RemoteConfigProvider
import soy.gabimoreno.danielnolasco.framework.provider.AppPreferencesProvider
import soy.gabimoreno.danielnolasco.framework.provider.FirebaseRemoteConfigProvider

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return Firebase.remoteConfig
    }

    @Provides
    @Singleton
    fun provideRemoteConfigProvider(firebaseRemoteConfig: FirebaseRemoteConfig): RemoteConfigProvider {
        return FirebaseRemoteConfigProvider(firebaseRemoteConfig)
    }

    @Provides
    @Singleton
    fun providePreferencesProvider(@ApplicationContext context: Context): PreferencesProvider {
        return AppPreferencesProvider(context)
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager {
        return context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNetworkRequest(): NetworkRequest {
        return NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }
}
