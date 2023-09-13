package soy.gabimoreno.danielnolasco.data.server

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import soy.gabimoreno.danielnolasco.BuildConfig
import soy.gabimoreno.danielnolasco.domain.provider.RemoteConfigProvider

class ApiNinjasClient(remoteConfigProvider: RemoteConfigProvider) {

    private var apiKey = DEFAULT_API_KEY

    val okHttpClient: OkHttpClient

    init {
        val requestInterceptor = Interceptor { chain ->
            val request = chain
                .request()
                .newBuilder()

            if (apiKey == DEFAULT_API_KEY) {
                apiKey = remoteConfigProvider.getApiNinjasXApiKey()
            }

            request.addHeader(X_API_KEY, apiKey)
            request.addHeader(CONTENT_TYPE, DEFAULT_CONTENT_TYPE)

            return@Interceptor chain.proceed(request.build())
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }
}

private const val X_API_KEY = "X-Api-Key"
private const val CONTENT_TYPE = "Content-Type"

private const val DEFAULT_CONTENT_TYPE = "application/json"
private const val DEFAULT_API_KEY = "N/A"
