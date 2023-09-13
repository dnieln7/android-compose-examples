package soy.gabimoreno.danielnolasco.ui.screen.walkinghistory

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.usecase.FormatEpochMillisecondsToReadableDateUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.FormatSecondsToReadableDurationUseCase
import soy.gabimoreno.danielnolasco.extensions.relaxedMockk
import soy.gabimoreno.danielnolasco.extensions.verifyOnce
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase

@ExperimentalCoroutinesApi
class WalkingHistoryViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var walkingSessionRepository: WalkingSessionRepository
    private lateinit var formatEpochMillisecondsToReadableDateUseCase: FormatEpochMillisecondsToReadableDateUseCase
    private lateinit var formatSecondsToReadableDurationUseCase: FormatSecondsToReadableDurationUseCase

    private lateinit var viewModel: WalkingHistoryViewModel

    @Before
    fun setup() {
        walkingSessionRepository = relaxedMockk()
        val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase = relaxedMockk()
        formatEpochMillisecondsToReadableDateUseCase = relaxedMockk()
        formatSecondsToReadableDurationUseCase = relaxedMockk()

        coEvery { walkingSessionRepository.getWalkingSessions() } returns emptyList<WalkingSession>().right()

        viewModel = WalkingHistoryViewModel(
            walkingSessionRepository,
            getErrorFromThrowableUseCase,
            formatEpochMillisecondsToReadableDateUseCase,
            formatSecondsToReadableDurationUseCase,
            dispatcher
        )
    }

    @Test
    fun `GIVEN a list WHEN getWalkingSessions THEN uiState should be WalkingHistoryState_Success`() {
        runTest(dispatcher) {
            viewModel.getWalkingSessions()

            viewModel.uiState.value shouldBeInstanceOf WalkingHistoryState.Success::class
        }
    }

    @Test
    fun `GIVEN a throwable WHEN getWalkingSessions THEN uiState should be WalkingHistoryState_Error`() {
        coEvery { walkingSessionRepository.getWalkingSessions() } returns Exception().left()

        runTest(dispatcher) {
            viewModel.getWalkingSessions()

            viewModel.uiState.value shouldBeInstanceOf WalkingHistoryState.Error::class
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
