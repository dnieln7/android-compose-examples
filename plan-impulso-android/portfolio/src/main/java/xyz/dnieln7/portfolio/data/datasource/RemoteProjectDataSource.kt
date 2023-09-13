package xyz.dnieln7.portfolio.data.datasource

import arrow.core.Either
import xyz.dnieln7.portfolio.data.mapper.toDomain
import xyz.dnieln7.portfolio.data.mapper.toResult
import xyz.dnieln7.portfolio.data.mapper.toUpdateProjectRequest
import xyz.dnieln7.portfolio.data.remote.PortfolioApiService
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.model.Result

class RemoteProjectDataSource(private val portfolioApiService: PortfolioApiService) {

    suspend fun getProjects(): Either<Throwable, List<Project>> {
        return Either.catch { portfolioApiService.getProjects().map { it.toDomain() } }
    }

    suspend fun updateProject(project: Project): Either<Throwable, Result> {
        val updateProjectRequest = project.toUpdateProjectRequest()

        return Either.catch {
            portfolioApiService.updateProject(
                updateProjectRequest.id,
                updateProjectRequest,
            ).toResult()
        }
    }

    suspend fun deleteProject(project: Project): Either<Throwable, Result> {
        return Either.catch { portfolioApiService.deleteProject(project.id).toResult() }
    }
}
