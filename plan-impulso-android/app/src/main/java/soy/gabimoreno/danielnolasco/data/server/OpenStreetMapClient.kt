package soy.gabimoreno.danielnolasco.data.server

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import soy.gabimoreno.danielnolasco.BuildConfig

class OpenStreetMapClient {

    val okHttpClient: OkHttpClient

    init {
        val requestInterceptor = Interceptor { chain ->
            val request = chain
                .request()
                .newBuilder()

            request.addHeader(CONTENT_TYPE, DEFAULT_CONTENT_TYPE)
            request.addHeader(ACCEPT, DEFAULT_ACCEPT)

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

private const val CONTENT_TYPE = "Content-Type"
private const val ACCEPT = "Accept"

private const val DEFAULT_CONTENT_TYPE = "application/json"
private const val DEFAULT_ACCEPT = "Accept"
