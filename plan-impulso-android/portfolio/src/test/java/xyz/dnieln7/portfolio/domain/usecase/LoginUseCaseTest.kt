package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.portfolio.data.repository.AUTH_EXPIRATION_IN_MINUTES
import xyz.dnieln7.portfolio.domain.repository.AuthRepository
import xyz.dnieln7.portfolio.extensions.relaxedMockk

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val resourceProvider = relaxedMockk<ResourceProvider>()
    private val authRepository = relaxedMockk<AuthRepository>()
    private val validateEmailUseCase = relaxedMockk<ValidateEmailUseCase>()
    private val validatePasswordUseCase = relaxedMockk<ValidatePasswordUseCase>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: LoginUseCase

    @Before
    fun setup() {
        useCase = LoginUseCase(
            resourceProvider = resourceProvider,
            authRepository = authRepository,
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordUseCase = validatePasswordUseCase,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THE return the expected DateTime`() {
        val expiration = DateTime.now(DateTimeZone.UTC).plusMinutes(AUTH_EXPIRATION_IN_MINUTES)

        every { validateEmailUseCase.invoke(any()) } returns true
        every { validatePasswordUseCase.invoke(any()) } returns true
        coEvery { authRepository.login(any(), any()) } returns expiration.right()

        runTest(dispatcher) {
            val result = useCase("test_email", "test_password").orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo expiration
        }
    }

    @Test
    fun `GIVEN an invalid email WHEN invoke THEN return the error message`() {
        val errorMessage = "Error message"

        every { validateEmailUseCase.invoke(any()) } returns false
        every { resourceProvider.getString(any()) } returns errorMessage

        runTest(dispatcher) {
            val result = useCase("test_email", "test_password").swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo errorMessage
        }
    }

    @Test
    fun `GIVEN an invalid password WHEN invoke THEN return the error message`() {
        val errorMessage = "Error message"

        every { validatePasswordUseCase.invoke(any()) } returns false
        every { resourceProvider.getString(any()) } returns errorMessage

        runTest(dispatcher) {
            val result = useCase("test_email", "test_password").swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo errorMessage
        }
    }

    @Test
    fun `GIVEN a throwable WHEN invoke THEN return the exception message`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)

        every { validateEmailUseCase.invoke(any()) } returns true
        every { validatePasswordUseCase.invoke(any()) } returns true
        coEvery { authRepository.login(any(), any()) } returns exception.left()
        every { getErrorFromThrowableUseCase.invoke(any()) } returns errorMessage

        runTest(dispatcher) {
            val result = useCase("test_email", "test_password").swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo errorMessage
        }
    }
}
