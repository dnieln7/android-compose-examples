package xyz.dnieln7.portfolio.domain.usecase

import org.junit.Before
import org.junit.Test
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.extensions.verifyOnce

class LogoutUseCaseTest {

    private val authPreferencesProvider = relaxedMockk<AuthPreferencesProvider>()

    private lateinit var useCase: LogoutUseCase

    @Before
    fun setup() {
        useCase = LogoutUseCase(authPreferencesProvider)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN clear() should be called once`() {
        useCase()

        verifyOnce {
            authPreferencesProvider.clear()
        }
    }
}
