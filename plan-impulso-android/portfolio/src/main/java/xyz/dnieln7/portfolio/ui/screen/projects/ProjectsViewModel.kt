package xyz.dnieln7.portfolio.ui.screen.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.core.domain.connectivity.Connectivity
import xyz.dnieln7.core.domain.connectivity.ConnectivityStatus
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.usecase.DeleteProjectUseCase
import xyz.dnieln7.portfolio.domain.usecase.GetProjectsUseCase
import xyz.dnieln7.portfolio.domain.usecase.IsUserSessionValidUseCase
import xyz.dnieln7.portfolio.domain.usecase.SyncProjectsUseCase

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val connectivity: Connectivity,
    private val syncProjectsUseCase: SyncProjectsUseCase,
    private val getProjectsUseCase: GetProjectsUseCase,
    private val isUserSessionValidUseCase: IsUserSessionValidUseCase,
    private val deleteProjectUseCase: DeleteProjectUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProjectsState())
    val uiState get() = _uiState.asStateFlow()

    fun getProjects() {
        viewModelScope.launch(dispatcher) {
            _uiState.update { it.copy(loading = true) }

            if (hasConnectivity()) {
                syncProjectsUseCase().fold(
                    { error ->
                        _uiState.update { it.copy(loading = false, data = null, error = error) }
                    },
                    { data ->
                        _uiState.update { it.copy(loading = false, data = data, error = null) }
                    }
                )
            } else {
                getProjectsUseCase().fold(
                    { error ->
                        _uiState.update { it.copy(loading = false, data = null, error = error) }
                    },
                    { data ->
                        _uiState.update { it.copy(loading = false, data = data, error = null) }
                    }
                )
            }
        }
    }

    fun refreshProjects() {
        viewModelScope.launch(dispatcher) {
            if (hasConnectivity()) {
                _uiState.update { it.copy(loading = true) }

                syncProjectsUseCase().fold(
                    { error ->
                        _uiState.update { it.copy(loading = false, data = null, error = error) }
                    },
                    { data ->
                        _uiState.update { it.copy(loading = false, data = data, error = null) }
                    }
                )
            }
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch(dispatcher) {
            if (isUserSessionValidUseCase()) {
                _uiState.update { it.copy(loading = true) }

                if (hasConnectivity()) {
                    deleteProject(project, true)
                } else {
                    deleteProject(project, false)
                }
            } else {
                _uiState.update {
                    it.copy(
                        loading = false,
                        deleted = null,
                        deleteError = null,
                        deleteBlocked = true
                    )
                }
            }
        }
    }

    fun resetDeleteState() {
        _uiState.update { it.copy(deleted = null, deleteError = null, deleteBlocked = false) }
    }

    private suspend fun hasConnectivity(): Boolean {
        return connectivity.checkConnectivity() == ConnectivityStatus.CONNECTED
    }

    private suspend fun deleteProject(project: Project, deleteInRemote: Boolean) {
        deleteProjectUseCase(project, deleteInRemote).fold(
            { error ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        deleted = null,
                        deleteError = error,
                        deleteBlocked = false
                    )
                }
            },
            {
                val data = _uiState.value.data ?: emptyList()
                val newData = data.filterNot { it.id == project.id }

                _uiState.update {
                    it.copy(
                        loading = false,
                        data = newData,
                        deleted = project,
                        deleteError = null,
                        deleteBlocked = false
                    )
                }
            }
        )
    }
}
