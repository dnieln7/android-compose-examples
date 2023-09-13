package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import javax.inject.Inject
import org.joda.time.DateTime
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.core.res.R
import xyz.dnieln7.portfolio.domain.repository.AuthRepository

class LoginUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val authRepository: AuthRepository,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(email: String, password: String): Either<String, DateTime> {
        if (!validateEmailUseCase(email)) {
            return resourceProvider.getString(R.string.email_not_valid).left()
        }

        if (!validatePasswordUseCase(password)) {
            return resourceProvider.getString(R.string.password_not_valid).left()
        }

        return authRepository.login(email, password).fold(
            {
                getErrorFromThrowableUseCase(it).left()
            },
            {
                it.right()
            }
        )
    }
}
