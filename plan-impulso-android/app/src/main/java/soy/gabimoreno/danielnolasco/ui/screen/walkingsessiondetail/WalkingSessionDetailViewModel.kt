package soy.gabimoreno.danielnolasco.ui.screen.walkingsessiondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.usecase.FixIncompleteLocationEventsUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.FormatEpochMillisecondsToReadableDateUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.FormatSecondsToReadableDurationUseCase
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.core.res.R

@HiltViewModel
class WalkingSessionDetailViewModel @Inject constructor(
    private val walkingSessionRepository: WalkingSessionRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
    private val fixIncompleteLocationEventsUseCase: FixIncompleteLocationEventsUseCase,
    private val formatEpochMillisecondsToReadableDateUseCase: FormatEpochMillisecondsToReadableDateUseCase,
    private val formatSecondsToReadableDurationUseCase: FormatSecondsToReadableDurationUseCase,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<WalkingSessionDetailState>(
        WalkingSessionDetailState.Loading
    )
    val uiState get() = _uiState.asStateFlow()

    fun getWalkingSessionByStartTime(startTime: Long) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(WalkingSessionDetailState.Loading)

            walkingSessionRepository.getWalkingSessionByStartTime(startTime).fold(
                {
                    val error = getErrorFromThrowableUseCase(it)

                    _uiState.emit(WalkingSessionDetailState.Error(message = error))
                },
                {
                    if (it != null) {
                        val incompleteLocationEvents = it.locationEvents.filter { locationEvent ->
                            locationEvent.displayName == null
                        }

                        val shouldReload = fixIncompleteLocationEventsUseCase(
                            incompleteLocationEvents
                        ).isNotEmpty()

                        if (shouldReload) {
                            getWalkingSessionByStartTime(it.startTime)
                        } else {
                            _uiState.emit(WalkingSessionDetailState.Success(it))
                        }
                    } else {
                        _uiState.emit(
                            WalkingSessionDetailState.Error(messageRes = R.string.not_found)
                        )
                    }
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
