package xyz.dnieln7.portfolio.data.repository

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBeNull
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.data.datasource.RemoteAuthDataSource
import xyz.dnieln7.portfolio.domain.exception.AuthenticationException
import xyz.dnieln7.portfolio.domain.provider.AuthPreferencesProvider
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.extensions.verifyOnce
import xyz.dnieln7.portfolio.fake.buildSuccessfulAuthentication
import xyz.dnieln7.portfolio.fake.buildUnSuccessfulAuthentication

@ExperimentalCoroutinesApi
class DefaultAuthRepositoryTest {
    private val dispatcher = UnconfinedTestDispatcher()

    private val remoteAuthDataSource = relaxedMockk<RemoteAuthDataSource>()
    private val authPreferencesProvider = relaxedMockk<AuthPreferencesProvider>()
    private val dateProvider = relaxedMockk<DateProvider>()

    private lateinit var repository: DefaultAuthRepository

    @Before
    fun setUp() {
        repository = DefaultAuthRepository(
            remoteAuthDataSource = remoteAuthDataSource,
            authPreferencesProvider = authPreferencesProvider,
            dateProvider = dateProvider
        )
    }

    @Test
    fun `GIVEN successful Authentication WHEN login THEN saveAuthToken and saveAuthExpiration should be called once`() {
        val expiration = DateTime.now(DateTimeZone.UTC)
        val authentication = buildSuccessfulAuthentication()

        coEvery { remoteAuthDataSource.login(any(), any()) } returns authentication.right()
        every { dateProvider.nowUTC() } returns expiration

        runTest(dispatcher) {
            repository.login("test_email", "test_password")

            verifyOnce {
                authPreferencesProvider.saveAuthToken(authentication.token)
                authPreferencesProvider.saveAuthExpiration(
                    expiration.plusMinutes(AUTH_EXPIRATION_IN_MINUTES).millis
                )
            }
        }
    }

    @Test
    fun `GIVEN successful Authentication WHEN login THEN return the expected DateTime`() {
        val expiration = DateTime.now(DateTimeZone.UTC)
        val authentication = buildSuccessfulAuthentication()

        coEvery { remoteAuthDataSource.login(any(), any()) } returns authentication.right()
        every { dateProvider.nowUTC() } returns expiration

        runTest(dispatcher) {
            val result = repository.login("test_email", "test_password").orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo expiration.plusMinutes(AUTH_EXPIRATION_IN_MINUTES)
        }
    }

    @Test
    fun `GIVEN unsuccessful Authentication WHEN login THEN get AuthenticationException with the expected message`() {
        val authentication = buildUnSuccessfulAuthentication()

        coEvery { remoteAuthDataSource.login(any(), any()) } returns authentication.right()

        runTest(dispatcher) {
            val result = repository.login("test_email", "test_password").swap().orNull()

            result.shouldNotBeNull()
            result shouldBeInstanceOf AuthenticationException::class
            result.message shouldBeEqualTo authentication.message
        }
    }

    @Test
    fun `GIVEN a throwable WHEN login THEN get the expected Exception`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)

        coEvery { remoteAuthDataSource.login(any(), any()) } returns exception.left()

        runTest(dispatcher) {
            val result = repository.login("test_email", "test_password").swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo exception
        }
    }
}
