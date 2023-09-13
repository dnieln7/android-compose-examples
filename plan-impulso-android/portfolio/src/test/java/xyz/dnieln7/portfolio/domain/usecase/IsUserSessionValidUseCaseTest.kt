package xyz.dnieln7.portfolio.domain.usecase

import io.mockk.every
import org.amshove.kluent.shouldBe
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.extensions.verifyOnce

class IsUserSessionValidUseCaseTest {

    private val authPreferencesProvider = relaxedMockk<AuthPreferencesProvider>()

    private lateinit var useCase: IsUserSessionValidUseCase

    @Before
    fun setup() {
        useCase = IsUserSessionValidUseCase(authPreferencesProvider, DateProvider())
    }

    @Test
    fun `GIVEN session valid WHEN invoke THEN return true`() {
        val tomorrow = DateTime.now(DateTimeZone.UTC).plusDays(1)

        every { authPreferencesProvider.getAuthExpiration() } returns tomorrow.millis

        val result = useCase()

        result shouldBe true
    }

    @Test
    fun `GIVEN session no longer valid WHEN invoke THEN return false`() {
        every { authPreferencesProvider.getAuthExpiration() } returns 0

        val result = useCase()

        result shouldBe false
    }

    @Test
    fun `GIVEN session no longer valid WHEN invoke THEN clear should be called once`() {
        every { authPreferencesProvider.getAuthExpiration() } returns 0

        useCase()

        verifyOnce {
            authPreferencesProvider.clear()
        }
    }
}
