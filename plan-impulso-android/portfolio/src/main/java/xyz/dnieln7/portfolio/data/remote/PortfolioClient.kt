package xyz.dnieln7.portfolio.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import xyz.dnieln7.portfolio.BuildConfig
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider

class PortfolioClient(private val authPreferencesProvider: AuthPreferencesProvider) {

    private var token: String = DEFAULT_AUTHORIZATION

    val okHttpClient: OkHttpClient

    init {
        val requestInterceptor = Interceptor { chain ->
            val request = chain
                .request()
                .newBuilder()

            authPreferencesProvider.getAuthToken()?.let { token = DEFAULT_AUTHORIZATION.plus(it) }

            request.addHeader(AUTHORIZATION, token)
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
private const val AUTHORIZATION = "Authorization"

private const val DEFAULT_CONTENT_TYPE = "application/json"
private const val DEFAULT_ACCEPT = "Accept"
private const val DEFAULT_AUTHORIZATION = "Bearer "
