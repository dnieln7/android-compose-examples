package xyz.dnieln7.portfolio.ui.screen.editproject

import xyz.dnieln7.portfolio.domain.model.Project

sealed class EditProjectState {
    object None : EditProjectState()
    object Loading : EditProjectState()
    object NotAuthenticated : EditProjectState()
    class Success(val data: Project, val update: Boolean) : EditProjectState()
    class UpdateError(val message: String, val project: Project) : EditProjectState()
    class Error(val message: String) : EditProjectState()
}
