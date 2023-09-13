package xyz.dnieln7.portfolio.domain.usecase

import javax.inject.Inject
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider

class IsUserSessionValidUseCase @Inject constructor(
    private val authPreferencesProvider: AuthPreferencesProvider,
    private val dateProvider: DateProvider,
) {

    operator fun invoke(): Boolean {
        val authExpiration = authPreferencesProvider.getAuthExpiration()
        val expiration = dateProvider.fromMillisUTC(authExpiration)
        val now = dateProvider.nowUTC()

        return if (now.isBefore(expiration)) {
            true
        } else {
            authPreferencesProvider.clear()

            false
        }
    }
}
