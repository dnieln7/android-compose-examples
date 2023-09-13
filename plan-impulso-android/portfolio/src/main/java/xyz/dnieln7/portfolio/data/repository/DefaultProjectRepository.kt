package xyz.dnieln7.portfolio.data.repository

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import timber.log.Timber
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.data.datasource.LocalProjectDataSource
import xyz.dnieln7.portfolio.data.datasource.RemoteProjectDataSource
import xyz.dnieln7.portfolio.data.mapper.DEFAULT_DELETED_AT
import xyz.dnieln7.portfolio.domain.exception.DeleteProjectException
import xyz.dnieln7.portfolio.domain.exception.UpdateProjectException
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository

class DefaultProjectRepository(
    private val dateProvider: DateProvider,
    private val remoteProjectDataSource: RemoteProjectDataSource,
    private val localProjectDataSource: LocalProjectDataSource,
) : ProjectRepository {

    override suspend fun getProjects(syncWithRemote: Boolean): Either<Throwable, List<Project>> {
        if (syncWithRemote) {
            remoteProjectDataSource.getProjects().fold(
                {
                    return it.left()
                },
                { remote ->
                    val local = localProjectDataSource.getProjects()
                    val merged = mutableListOf<Project>()

                    remote.forEach { projectApi ->
                        val projectDb = local.find { it.id == projectApi.id }

                        if (projectDb != null) {
                            val projectDbUpdatedAt = dateProvider.fromISO8601(projectDb.updatedAt)
                            val projectApiUpdatedAt = dateProvider.fromISO8601(projectApi.updatedAt)

                            if (projectDb.deletedAt > DEFAULT_DELETED_AT) {
                                deleteProjectInRemote(projectDb).fold(
                                    {
                                        return it.left()
                                    },
                                    {
                                        Timber.i("Deleted project: ${projectDb.id}")
                                    }
                                )
                            } else if (projectApiUpdatedAt.isBefore(projectDbUpdatedAt)) {
                                updateProjectInRemote(projectDb).fold(
                                    {
                                        return it.left()
                                    },
                                    {
                                        merged.add(projectDb)
                                    }
                                )
                            } else {
                                merged.add(projectApi)
                            }
                        } else {
                            merged.add(projectApi)
                        }
                    }

                    localProjectDataSource.deleteProjects()
                    localProjectDataSource.insertProjects(merged)
                }
            )
        }

        return Either.catch { localProjectDataSource.getProjects() }
    }

    override suspend fun getProjectById(id: Int): Either<Throwable, Project?> {
        return Either.catch { localProjectDataSource.getProjectById(id) }
    }

    override suspend fun updateProject(
        project: Project,
        updateInRemote: Boolean,
    ): Either<Throwable, Project> {
        val toUpdate = listOf(project)

        if (updateInRemote) {
            updateProjectInRemote(project).fold(
                {
                    return it.left()
                },
                {
                    localProjectDataSource.insertProjects(toUpdate)
                }
            )
        } else {
            localProjectDataSource.insertProjects(toUpdate)
        }

        return Either.catch { localProjectDataSource.getProjectById(project.id)!! }
    }

    private suspend fun updateProjectInRemote(project: Project): Either<Throwable, Boolean> {
        return remoteProjectDataSource.updateProject(project).fold(
            {
                it.left()
            },
            {
                if (it.successful) {
                    true.right()
                } else {
                    UpdateProjectException(it.message).left()
                }
            }
        )
    }

    override suspend fun deleteProject(
        project: Project,
        deleteInRemote: Boolean
    ): Either<Throwable, Unit> {
        return if (deleteInRemote) {
            deleteProjectInRemote(project).fold(
                {
                    it.left()
                },
                {
                    localProjectDataSource.deleteProject(project)
                    Unit.right()
                }
            )
        } else {
            localProjectDataSource.markAsDeleted(project.id, dateProvider.nowUTC().millis)

            Unit.right()
        }
    }

    private suspend fun deleteProjectInRemote(project: Project): Either<Throwable, Unit> {
        return remoteProjectDataSource.deleteProject(project).fold(
            {
                it.left()
            },
            {
                if (it.successful) {
                    Unit.right()
                } else {
                    DeleteProjectException(it.message).left()
                }
            },
        )
    }
}
