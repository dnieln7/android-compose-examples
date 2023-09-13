package soy.gabimoreno.danielnolasco.framework.provider

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.internal.ConfigFetchHandler
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import soy.gabimoreno.danielnolasco.R
import soy.gabimoreno.danielnolasco.domain.provider.RemoteConfigProvider
import timber.log.Timber
import xyz.dnieln7.portfolio.BuildConfig

class FirebaseRemoteConfigProvider(private val firebaseRemoteConfig: FirebaseRemoteConfig) :
    RemoteConfigProvider {

    init {
        initFirebaseRemoteConfig()
        fetchFirebaseRemoteConfig()
    }

    private fun initFirebaseRemoteConfig() {
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                0
            } else {
                ConfigFetchHandler.DEFAULT_MINIMUM_FETCH_INTERVAL_IN_SECONDS
            }
        }

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
    }

    private fun fetchFirebaseRemoteConfig() {
        firebaseRemoteConfig.fetchAndActivate().addOnCompleteListener {
            Timber.i("Fetch and activate firebase remote config? ${it.isSuccessful}")
        }
    }

    override fun getApiNinjasXApiKey(): String {
        return firebaseRemoteConfig.getString(API_NINJAS_X_API_KEY)
    }
}

private const val API_NINJAS_X_API_KEY = "API_NINJAS_X_API_KEY"
