package xyz.dnieln7.portfolio.ui.screen.projects

import xyz.dnieln7.portfolio.domain.model.Project

data class ProjectsState(
    val loading: Boolean = false,
    val data: List<Project>? = null,
    val error: String? = null,
    val deleted: Project? = null,
    val deleteError: String? = null,
    val deleteBlocked: Boolean = false,
)
