package soy.gabimoreno.danielnolasco.ui.screen.walkingplayground

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.usecase.FormatSecondsToChronometerUseCase
import soy.gabimoreno.danielnolasco.framework.location.FusedLocationSettings
import soy.gabimoreno.danielnolasco.framework.service.LocationService
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.domain.provider.DateProvider

@HiltViewModel
class WalkingPlaygroundViewModel @Inject constructor(
    private val dateProvider: DateProvider,
    private val fusedLocationSettings: FusedLocationSettings,
    private val walkingSessionRepository: WalkingSessionRepository,
    private val locationEventRepository: LocationEventRepository,
    private val formatSecondsToChronometerUseCase: FormatSecondsToChronometerUseCase,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private var _startTime: Long = LocationService.DEFAULT_START_TIME
    val startTime get() = if (_startTime != LocationService.DEFAULT_START_TIME) _startTime else null

    private val _uiState = MutableStateFlow(WalkingPlaygroundState())
    val uiState get() = _uiState.asStateFlow()

    fun startSession() {
        viewModelScope.launch(dispatcher) {
            fusedLocationSettings.checkLocationSettings().fold(
                { exception ->
                    if (exception is ResolvableApiException) {
                        _uiState.update {
                            it.copy(
                                hasLocationCapabilities = false,
                                locationCapabilitiesException = exception,
                            )
                        }
                    } else {
                        _uiState.update { it.copy(hasLocationCapabilities = false) }
                    }
                },
                { hasLocationCapabilities ->
                    if (hasLocationCapabilities) {
                        _startTime = dateProvider.nowUTC().millis

                        _uiState.update { it.copy(isReady = true) }
                    } else {
                        _uiState.update { it.copy(hasLocationCapabilities = false) }
                    }
                }
            )
        }
    }

    fun onServiceStarted() {
        _uiState.update {
            it.copy(
                isReady = false,
                isStarted = true,
                locationEvents = locationEventRepository.observeLocationEventsByOwnerStartTime(
                    _startTime
                )
            )
        }
    }

    fun onTimerTick(seconds: Long) {
        val chronometer = formatSecondsToChronometerUseCase(seconds)

        _uiState.update { it.copy(seconds = chronometer) }
    }

    fun finishSession() {
        viewModelScope.launch(dispatcher) {
            val endTime = dateProvider.nowUTC().millis

            walkingSessionRepository.updateWalkingSessionAsFinished(_startTime, endTime)

            _uiState.emit(WalkingPlaygroundState())
        }
    }
}
