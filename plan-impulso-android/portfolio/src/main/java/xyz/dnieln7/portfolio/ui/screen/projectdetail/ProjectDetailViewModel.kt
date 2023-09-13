package xyz.dnieln7.portfolio.ui.screen.projectdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.ui.UiState
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.usecase.GetProjectByIdUseCase
import xyz.dnieln7.portfolio.domain.usecase.IsUserSessionValidUseCase

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val isUserSessionValidUseCase: IsUserSessionValidUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Project>>(UiState.None)
    val uiState get() = _uiState.asStateFlow()

    private val _editButtonState = MutableStateFlow<EditButtonState>(EditButtonState.Invisible)
    val editButtonState get() = _editButtonState.asStateFlow()

    fun updateEditButtonState() {
        viewModelScope.launch(dispatcher) {
            if (isUserSessionValidUseCase()) {
                _editButtonState.emit(EditButtonState.Visible)
            } else {
                _editButtonState.emit(EditButtonState.Invisible)
            }
        }
    }

    fun getProjectById(id: Int) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(UiState.Loading)

            getProjectByIdUseCase(id).fold(
                {
                    _uiState.emit(UiState.Error(it))
                },
                {
                    _uiState.emit(UiState.Success(it))
                }
            )
        }
    }
}
