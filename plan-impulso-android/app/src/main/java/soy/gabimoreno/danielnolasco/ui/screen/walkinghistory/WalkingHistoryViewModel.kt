package soy.gabimoreno.danielnolasco.ui.screen.walkinghistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.usecase.FormatEpochMillisecondsToReadableDateUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.FormatSecondsToReadableDurationUseCase
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase

@HiltViewModel
class WalkingHistoryViewModel @Inject constructor(
    private val walkingSessionRepository: WalkingSessionRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
    private val formatEpochMillisecondsToReadableDateUseCase: FormatEpochMillisecondsToReadableDateUseCase,
    private val formatSecondsToReadableDurationUseCase: FormatSecondsToReadableDurationUseCase,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<WalkingHistoryState>(WalkingHistoryState.Loading)
    val uiState get() = _uiState.asStateFlow()

    init {
        getWalkingSessions()
    }

    fun getWalkingSessions() {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(WalkingHistoryState.Loading)

            walkingSessionRepository.getWalkingSessions().fold(
                {
                    val error = getErrorFromThrowableUseCase(it)

                    _uiState.emit(WalkingHistoryState.Error(message = error))
                },
                {
                    _uiState.emit(WalkingHistoryState.Success(it))
                }
            )
        }
    }

    fun formatStartTime(startTime: Long): String {
        return formatEpochMillisecondsToReadableDateUseCase(startTime)
    }

    fun formatDuration(duration: Long): String {
        return formatSecondsToReadableDurationUseCase(duration)
    }
}
