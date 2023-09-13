package soy.gabimoreno.danielnolasco.ui.screen.doglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.model.Dog
import soy.gabimoreno.danielnolasco.domain.repository.dog.DogRepository
import soy.gabimoreno.danielnolasco.domain.usecase.GetDogApiServiceErrorUseCase
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.domain.connectivity.Connectivity
import xyz.dnieln7.core.domain.connectivity.ConnectivityStatus

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val dogRepository: DogRepository,
    private val getDogApiServiceErrorUseCase: GetDogApiServiceErrorUseCase,
    private val connectivity: Connectivity,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow<Flow<PagingData<Dog>>>(emptyFlow())
    val uiState get() = _uiState.asStateFlow()

    fun getDogs() {
        viewModelScope.launch(dispatcher) {
            if (connectivity.checkConnectivity() == ConnectivityStatus.CONNECTED) {
                _uiState.emit(dogRepository.getDogs())
            } else {
                _uiState.emit(dogRepository.getLocalDogs())
            }
        }
    }

    fun getDogApiServiceError(throwable: Throwable): String {
        return getDogApiServiceErrorUseCase(throwable)
    }
}
