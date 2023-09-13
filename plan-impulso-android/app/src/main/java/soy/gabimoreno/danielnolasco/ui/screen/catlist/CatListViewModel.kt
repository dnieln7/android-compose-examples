package soy.gabimoreno.danielnolasco.ui.screen.catlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.repository.cat.CatRepository
import xyz.dnieln7.core.di.IO

@HiltViewModel
class CatListViewModel @Inject constructor(
    private val catRepository: CatRepository,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _catListState = MutableStateFlow<CatListState>(CatListState.Loading)
    val catListState get() = _catListState.asStateFlow()

    fun getCats() {
        viewModelScope.launch(dispatcher) {
            val cats = catRepository.getCats()

            _catListState.emit(CatListState.Success(cats))
        }
    }
}
