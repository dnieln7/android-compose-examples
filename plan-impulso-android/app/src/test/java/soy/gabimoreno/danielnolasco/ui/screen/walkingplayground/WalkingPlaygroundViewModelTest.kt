package soy.gabimoreno.danielnolasco.ui.screen.walkingplayground

import arrow.core.left
import arrow.core.right
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.usecase.FormatSecondsToChronometerUseCase
import soy.gabimoreno.danielnolasco.extensions.coVerifyOnce
import soy.gabimoreno.danielnolasco.extensions.relaxedMockk
import soy.gabimoreno.danielnolasco.framework.location.FusedLocationSettings
import xyz.dnieln7.core.domain.provider.DateProvider

@ExperimentalCoroutinesApi
class WalkingPlaygroundViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val dateProvider = relaxedMockk<DateProvider>()
    private val fusedLocationSettings = relaxedMockk<FusedLocationSettings>()
    private val walkingSessionRepository = relaxedMockk<WalkingSessionRepository>()
    private val formatSecondsToChronometerUseCase =
        relaxedMockk<FormatSecondsToChronometerUseCase>()

    private lateinit var viewModel: WalkingPlaygroundViewModel

    @Before
    fun setup() {
        val locationEventRepository: LocationEventRepository = relaxedMockk()

        viewModel = WalkingPlaygroundViewModel(
            dateProvider,
            fusedLocationSettings,
            walkingSessionRepository,
            locationEventRepository,
            formatSecondsToChronometerUseCase,
            dispatcher,
        )
    }

    @Test
    fun `GIVEN locationCapabilities WHEN startSession THEN uiState isReady and hasLocationCapabilities should be true`() {
        coEvery { fusedLocationSettings.checkLocationSettings() } returns true.right()

        runTest(dispatcher) {
            viewModel.startSession()

            viewModel.uiState.value.isReady.shouldBeTrue()
            viewModel.uiState.value.hasLocationCapabilities.shouldBeTrue()
        }
    }

    @Test
    fun `GIVEN no locationCapabilities WHEN startSession THEN uiState isReady and hasLocationCapabilities should be false`() {
        coEvery { fusedLocationSettings.checkLocationSettings() } returns false.right()

        runTest(dispatcher) {
            viewModel.startSession()

            viewModel.uiState.value.isReady.shouldBeFalse()
            viewModel.uiState.value.hasLocationCapabilities.shouldBeFalse()
        }
    }

    @Test
    fun `GIVEN a throwable WHEN startSession THEN uiState isReady and hasLocationCapabilities should be false`() {
        coEvery { fusedLocationSettings.checkLocationSettings() } returns Exception().left()

        runTest(dispatcher) {
            viewModel.startSession()

            viewModel.uiState.value.isReady.shouldBeFalse()
            viewModel.uiState.value.hasLocationCapabilities.shouldBeFalse()
        }
    }

    @Test
    fun `GIVEN a ResolvableApiException WHEN startSession THEN uiState locationCapabilitiesException should not be null`() {
        coEvery { fusedLocationSettings.checkLocationSettings() } returns ResolvableApiException(
            Status.RESULT_SUCCESS
        ).left()

        runTest(dispatcher) {
            viewModel.startSession()

            viewModel.uiState.value.locationCapabilitiesException.shouldNotBeNull()
        }
    }

    @Test
    fun `GIVEN the happy path WHEN onServiceStarted THEN uiState isReady should be false and isStarted should be true`() {
        runTest(dispatcher) {
            viewModel.onServiceStarted()

            viewModel.uiState.value.isReady.shouldBeFalse()
            viewModel.uiState.value.isStarted.shouldBeTrue()
        }
    }

    @Test
    fun `GIVEN the happy path WHEN onTimerTick THEN uiState seconds should have the expected seconds`() {
        val seconds = 60L
        val expectedResult = "00:01:00"

        every { formatSecondsToChronometerUseCase.invoke(any()) } returns expectedResult

        runTest(dispatcher) {
            viewModel.onTimerTick(seconds)

            viewModel.uiState.value.seconds shouldBeEqualTo expectedResult
        }
    }

    @Test
    fun `GIVEN the happy path WHEN finishSession THEN uiState should be reset`() {
        runTest(dispatcher) {
            viewModel.finishSession()

            viewModel.uiState.value shouldBeEqualTo WalkingPlaygroundState()
        }
    }

    @Test
    fun `GIVEN the happy path WHEN finishSession THEN updateWalkingSessionAsFinished should be called once`() {
        val dateTime = DateTime(
            /* year = */ 2021,
            /* monthOfYear = */ 10,
            /* dayOfMonth = */ 9,
            /* hourOfDay = */ 19,
            /* minuteOfHour = */ 22,
            /* secondOfMinute = */ 51,
            /* millisOfSecond = */ 770,
            /* zone = */ DateTimeZone.UTC,
        )

        every { dateProvider.nowUTC() } returns dateTime

        runTest(dispatcher) {
            viewModel.finishSession()

            coVerifyOnce {
                walkingSessionRepository.updateWalkingSessionAsFinished(any(), dateTime.millis)
            }
        }
    }
}
