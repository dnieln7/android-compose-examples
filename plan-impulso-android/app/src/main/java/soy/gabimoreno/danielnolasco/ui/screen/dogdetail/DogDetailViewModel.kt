package soy.gabimoreno.danielnolasco.ui.screen.dogdetail

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.model.Dog
import soy.gabimoreno.danielnolasco.domain.repository.dog.DogRepository
import soy.gabimoreno.danielnolasco.domain.usecase.GetDogApiServiceErrorUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.GetLevelIndicatorColorUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.GetLevelIndicatorLevelUseCase
import soy.gabimoreno.danielnolasco.framework.notification.DOG_NOTIFICATION_ID
import soy.gabimoreno.danielnolasco.framework.notification.NotificationManager
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.res.R

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val dogRepository: DogRepository,
    private val getLevelIndicatorColorUseCase: GetLevelIndicatorColorUseCase,
    private val getLevelIndicatorLevelUseCase: GetLevelIndicatorLevelUseCase,
    private val getDogApiServiceErrorUseCase: GetDogApiServiceErrorUseCase,
    @IO private val dispatcher: CoroutineDispatcher,
    private val notificationManager: NotificationManager,
) : ViewModel() {

    private val _uiState = MutableStateFlow<DogDetailState>(DogDetailState.Loading)
    val uiState get() = _uiState.asStateFlow()

    fun getDogByName(name: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(DogDetailState.Loading)

            dogRepository.getDogByName(name).fold(
                ifLeft = {
                    val error = getDogApiServiceErrorUseCase(it)

                    _uiState.emit(DogDetailState.Error(message = error))
                },
                ifRight = {
                    if (it != null) {
                        _uiState.emit(DogDetailState.Success(it))
                    } else {
                        _uiState.emit(DogDetailState.Error(messageRes = R.string.not_found))
                    }
                }
            )
        }
    }

    fun getLevelIndicatorColor(level: Float): Color {
        return getLevelIndicatorColorUseCase(level)
    }

    fun getLevelIndicatorLevel(rawLevel: Int): Float {
        return getLevelIndicatorLevelUseCase(rawLevel)
    }

    fun displayInNotification(dog: Dog) {
        notificationManager.buildDogNotification(dog).also {
            notificationManager.launchNotification(DOG_NOTIFICATION_ID, it)
        }
    }
}
