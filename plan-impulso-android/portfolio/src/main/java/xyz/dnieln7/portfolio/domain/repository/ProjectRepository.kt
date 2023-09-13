package xyz.dnieln7.portfolio.domain.repository

import arrow.core.Either
import xyz.dnieln7.portfolio.domain.model.Project

interface ProjectRepository {
    suspend fun getProjects(syncWithRemote: Boolean): Either<Throwable, List<Project>>
    suspend fun getProjectById(id: Int): Either<Throwable, Project?>
    suspend fun updateProject(project: Project, updateInRemote: Boolean): Either<Throwable, Project>
    suspend fun deleteProject(project: Project, deleteInRemote: Boolean): Either<Throwable, Unit>
}
