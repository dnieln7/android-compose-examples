package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import javax.inject.Inject
import xyz.dnieln7.core.domain.extensions.stringifyToPortfolioFormat
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository

class UpdateProjectUseCase @Inject constructor(
    private val dateProvider: DateProvider,
    private val projectRepository: ProjectRepository,
    private val getPortfolioApiServiceErrorUseCase: GetPortfolioApiServiceErrorUseCase,
) {

    suspend operator fun invoke(project: Project): Either<String, Project> {
        val updatedAt = dateProvider.nowUTC().stringifyToPortfolioFormat()
        val newProject = project.copy(updatedAt = updatedAt)

        return projectRepository.updateProject(newProject, true).fold(
            {
                getPortfolioApiServiceErrorUseCase(it).left()
            },
            {
                it.right()
            }
        )
    }
}
