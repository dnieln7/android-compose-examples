package xyz.dnieln7.portfolio.ui.screen.editproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.domain.connectivity.Connectivity
import xyz.dnieln7.core.domain.connectivity.ConnectivityStatus
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.usecase.GetProjectByIdUseCase
import xyz.dnieln7.portfolio.domain.usecase.IsUserSessionValidUseCase
import xyz.dnieln7.portfolio.domain.usecase.UpdateProjectLocalUseCase
import xyz.dnieln7.portfolio.domain.usecase.UpdateProjectUseCase

@HiltViewModel
class EditProjectViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val connectivity: Connectivity,
    private val isUserSessionValidUseCase: IsUserSessionValidUseCase,
    private val getProjectByIdUseCase: GetProjectByIdUseCase,
    private val updateProjectUseCase: UpdateProjectUseCase,
    private val updateProjectLocalUseCase: UpdateProjectLocalUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditProjectState>(EditProjectState.None)
    val uiState get() = _uiState.asStateFlow()

    fun getProjectById(id: Int) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(EditProjectState.Loading)

            getProjectByIdUseCase(id).fold(
                {
                    _uiState.emit(EditProjectState.Error(it))
                },
                {
                    _uiState.emit(EditProjectState.Success(it, false))
                }
            )
        }
    }

    fun updateProject(project: Project) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(EditProjectState.Loading)
            if (isUserSessionValidUseCase()) {
                if (hasConnectivity()) {
                    updateProjectUseCase(project).fold(
                        {
                            _uiState.emit(EditProjectState.UpdateError(it, project))
                        },
                        {
                            _uiState.emit(EditProjectState.Success(it, true))
                        }
                    )
                } else {
                    updateProjectLocalUseCase(project).fold(
                        {
                            _uiState.emit(EditProjectState.UpdateError(it, project))
                        },
                        {
                            _uiState.emit(EditProjectState.Success(it, true))
                        }
                    )
                }
            } else {
                _uiState.emit(EditProjectState.NotAuthenticated)
            }
        }
    }

    fun onProjectUpdated(project: Project) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(EditProjectState.Success(project, false))
        }
    }

    private suspend fun hasConnectivity(): Boolean {
        return connectivity.checkConnectivity() == ConnectivityStatus.CONNECTED
    }
}
