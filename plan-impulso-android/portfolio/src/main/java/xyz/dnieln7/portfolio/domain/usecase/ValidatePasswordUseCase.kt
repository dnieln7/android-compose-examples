package xyz.dnieln7.portfolio.domain.usecase

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): Boolean {
        return password.length >= MINIMUM_PASSWORD_LENGTH
    }
}

private const val MINIMUM_PASSWORD_LENGTH = 4
