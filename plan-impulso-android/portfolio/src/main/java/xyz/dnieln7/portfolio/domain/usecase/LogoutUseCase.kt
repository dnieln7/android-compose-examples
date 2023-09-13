package xyz.dnieln7.portfolio.domain.usecase

import javax.inject.Inject
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider

class LogoutUseCase @Inject constructor(
    private val authPreferencesProvider: AuthPreferencesProvider
) {

    operator fun invoke() {
        authPreferencesProvider.clear()
    }
}
