package xyz.dnieln7.portfolio.ui.screen.projects

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.portfolio.data.repository.AUTH_EXPIRATION_IN_MINUTES
import xyz.dnieln7.portfolio.domain.usecase.IsUserSessionValidUseCase
import xyz.dnieln7.portfolio.domain.usecase.LoginUseCase
import xyz.dnieln7.portfolio.domain.usecase.LogoutUseCase
import xyz.dnieln7.portfolio.extensions.awaitItemTest
import xyz.dnieln7.portfolio.extensions.relaxedMockk

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val isUserSessionValidUseCase = relaxedMockk<IsUserSessionValidUseCase>()
    private val loginUseCase = relaxedMockk<LoginUseCase>()
    private val logoutUseCase = relaxedMockk<LogoutUseCase>()

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setup() {
        viewModel = AuthViewModel(
            dispatcher = dispatcher,
            isUserSessionValidUseCase = isUserSessionValidUseCase,
            loginUseCase = loginUseCase,
            logoutUseCase = logoutUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN uiState should be AuthState_LoggedOut`() {
        runTest(dispatcher) {
            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf AuthState.LoggedOut::class }
        }
    }

    @Test
    fun `GIVEN true WHEN checkUserSession THEN uiState should be AuthState_LoggedIn`() {
        every { isUserSessionValidUseCase.invoke() } returns true

        runTest(dispatcher) {
            viewModel.checkUserSession()

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf AuthState.LoggedIn::class }
        }
    }

    @Test
    fun `GIVEN false WHEN checkUserSession THEN uiState should be AuthState_LoggedOut`() {
        every { isUserSessionValidUseCase.invoke() } returns false

        runTest(dispatcher) {
            viewModel.checkUserSession()

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf AuthState.LoggedOut::class }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN login THEN uiState should be AuthState_LoggedIn`() {
        val expiration = DateTime.now(DateTimeZone.UTC).plusMinutes(AUTH_EXPIRATION_IN_MINUTES)

        coEvery { loginUseCase.invoke(any(), any()) } returns expiration.right()

        runTest(dispatcher) {
            viewModel.login("test_email", "test_password")

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf AuthState.LoggedIn::class }
        }
    }

    @Test
    fun `GIVEN a String WHEN login THEN uiState should be AuthState_LoginError`() {
        coEvery { loginUseCase.invoke(any(), any()) } returns "".left()

        runTest(dispatcher) {
            viewModel.login("test_email", "test_password")

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf AuthState.LoginError::class }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN logout THEN uiState should be AuthState_LoggedOut`() {
        runTest(dispatcher) {
            viewModel.logout()

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf AuthState.LoggedOut::class }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN onLoginErrorShown THEN uiState should be AuthState_LoggedOut`() {
        runTest(dispatcher) {
            viewModel.onLoginErrorShown()

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf AuthState.LoggedOut::class }
        }
    }
}
