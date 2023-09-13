package soy.gabimoreno.danielnolasco.ui.screen.catdetail

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.model.Cat
import soy.gabimoreno.danielnolasco.domain.repository.cat.CatRepository
import soy.gabimoreno.danielnolasco.domain.usecase.GetLevelIndicatorColorUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.GetLevelIndicatorLevelUseCase
import soy.gabimoreno.danielnolasco.framework.notification.CAT_NOTIFICATION_ID
import soy.gabimoreno.danielnolasco.framework.notification.NotificationManager
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.res.R

@HiltViewModel
class CatDetailViewModel @Inject constructor(
    private val catRepository: CatRepository,
    private val getLevelIndicatorColorUseCase: GetLevelIndicatorColorUseCase,
    private val getLevelIndicatorLevelUseCase: GetLevelIndicatorLevelUseCase,
    @IO private val dispatcher: CoroutineDispatcher,
    private val notificationManager: NotificationManager,
) : ViewModel() {

    private val _catDetailState = MutableStateFlow<CatDetailState>(CatDetailState.Loading)
    val catDetailState get() = _catDetailState.asStateFlow()

    fun getCatByName(name: String) {
        viewModelScope.launch(dispatcher) {
            val cat = catRepository.getCatByName(name)

            if (cat != null) {
                _catDetailState.emit(CatDetailState.Success(cat))
            } else {
                _catDetailState.emit(CatDetailState.Error(messageRes = R.string.not_found))
            }
        }
    }

    fun getLevelIndicatorColor(level: Float): Color {
        return getLevelIndicatorColorUseCase(level)
    }

    fun getLevelIndicatorLevel(rawLevel: Int): Float {
        return getLevelIndicatorLevelUseCase(rawLevel)
    }

    fun displayInNotification(cat: Cat) {
        notificationManager.buildCatNotification(cat).also {
            notificationManager.launchNotification(CAT_NOTIFICATION_ID, it)
        }
    }
}
