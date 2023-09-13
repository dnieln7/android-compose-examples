package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import javax.inject.Inject
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository

class DeleteProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val getPortfolioApiServiceErrorUseCase: GetPortfolioApiServiceErrorUseCase,
) {

    suspend operator fun invoke(project: Project, deleteInRemote: Boolean): Either<String, Unit> {
        return projectRepository.deleteProject(project, deleteInRemote).fold(
            {
                getPortfolioApiServiceErrorUseCase(it).left()
            },
            {
                Unit.right()
            }
        )
    }
}
