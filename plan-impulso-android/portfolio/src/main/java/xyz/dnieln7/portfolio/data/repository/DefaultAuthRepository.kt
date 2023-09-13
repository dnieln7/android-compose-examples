package xyz.dnieln7.portfolio.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import org.joda.time.DateTime
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.data.datasource.RemoteAuthDataSource
import xyz.dnieln7.portfolio.domain.exception.AuthenticationException
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider
import xyz.dnieln7.portfolio.domain.repository.AuthRepository

class DefaultAuthRepository(
    private val remoteAuthDataSource: RemoteAuthDataSource,
    private val authPreferencesProvider: AuthPreferencesProvider,
    private val dateProvider: DateProvider,
) : AuthRepository {

    override suspend fun login(email: String, password: String): Either<Throwable, DateTime> {
        return remoteAuthDataSource.login(email, password).fold(
            {
                it.left()
            },
            {
                if (it.successful) {
                    val expiration = dateProvider.nowUTC().plusMinutes(AUTH_EXPIRATION_IN_MINUTES)

                    authPreferencesProvider.saveAuthToken(it.token)
                    authPreferencesProvider.saveAuthExpiration(expiration.millis)

                    expiration.right()
                } else {
                    AuthenticationException(it.message).left()
                }
            }
        )
    }
}

const val AUTH_EXPIRATION_IN_MINUTES = 55
