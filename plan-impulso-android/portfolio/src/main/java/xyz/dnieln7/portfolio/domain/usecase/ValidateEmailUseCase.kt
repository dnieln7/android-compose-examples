package xyz.dnieln7.portfolio.domain.usecase

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): Boolean {
        return EMAIL_REGEX.toRegex().matches(email)
    }
}

private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
