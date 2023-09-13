package soy.gabimoreno.danielnolasco.ui.screen.walkingsessiondetail

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.usecase.FixIncompleteLocationEventsUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.FormatEpochMillisecondsToReadableDateUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.FormatSecondsToReadableDurationUseCase
import soy.gabimoreno.danielnolasco.extensions.relaxedMockk
import soy.gabimoreno.danielnolasco.extensions.verifyOnce
import soy.gabimoreno.danielnolasco.fake.buildWalkingSession
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase

@ExperimentalCoroutinesApi
class WalkingSessionDetailViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val walkingSessionRepository = relaxedMockk<WalkingSessionRepository>()
    private val formatEpochMillisecondsToReadableDateUseCase =
        relaxedMockk<FormatEpochMillisecondsToReadableDateUseCase>()
    private val formatSecondsToReadableDurationUseCase =
        relaxedMockk<FormatSecondsToReadableDurationUseCase>()

    private lateinit var viewModel: WalkingSessionDetailViewModel

    @Before
    fun setup() {
        val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()
        val fixIncompleteLocationEventsUseCase = relaxedMockk<FixIncompleteLocationEventsUseCase>()

        viewModel = WalkingSessionDetailViewModel(
            walkingSessionRepository,
            getErrorFromThrowableUseCase,
            fixIncompleteLocationEventsUseCase,
            formatEpochMillisecondsToReadableDateUseCase,
            formatSecondsToReadableDurationUseCase,
            dispatcher
        )
    }

    @Test
    fun `GIVEN a WalkingSession WHEN getWalkingSessionByStartTime THEN uiState should be WalkingSessionDetailState_Success`() {
        coEvery { walkingSessionRepository.getWalkingSessionByStartTime(any()) } returns buildWalkingSession().right()

        runTest(dispatcher) {
            viewModel.getWalkingSessionByStartTime(0)

            viewModel.uiState.value shouldBeInstanceOf WalkingSessionDetailState.Success::class
        }
    }

    @Test
    fun `GIVEN null WHEN getWalkingSessionByStartTime THEN uiState should be WalkingSessionDetailState_Error`() {
        coEvery { walkingSessionRepository.getWalkingSessionByStartTime(any()) } returns null.right()

        runTest(dispatcher) {
            viewModel.getWalkingSessionByStartTime(0)

            viewModel.uiState.value shouldBeInstanceOf WalkingSessionDetailState.Error::class
        }
    }

    @Test
    fun `GIVEN a throwable WHEN getWalkingSessionByStartTime THEN uiState should be WalkingSessionDetailState_Error`() {
        coEvery { walkingSessionRepository.getWalkingSessionByStartTime(any()) } returns Exception().left()

        runTest(dispatcher) {
            viewModel.getWalkingSessionByStartTime(0)

            viewModel.uiState.value shouldBeInstanceOf WalkingSessionDetailState.Error::class
        }
    }

    @Test
    fun `GIVEN the happy path WHEN formatStartTime THEN formatEpochMillisecondsToReadableDateUseCase should be called once`() {
        runTest(dispatcher) {
            viewModel.formatStartTime(0)

            verifyOnce { formatEpochMillisecondsToReadableDateUseCase(any()) }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN formatDuration THEN formatSecondsToReadableDurationUseCase should be called once`() {
        runTest(dispatcher) {
            viewModel.formatDuration(0)

            verifyOnce { formatSecondsToReadableDurationUseCase(any()) }
        }
    }
}
