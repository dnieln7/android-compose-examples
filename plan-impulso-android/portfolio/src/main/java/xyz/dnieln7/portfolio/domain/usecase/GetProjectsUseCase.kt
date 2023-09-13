package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import javax.inject.Inject
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository

class GetProjectsUseCase @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase
) {

    suspend operator fun invoke(): Either<String, List<Project>> {
        return projectRepository.getProjects(false).fold(
            {
                getErrorFromThrowableUseCase(it).left()
            },
            {
                it.right()
            }
        )
    }
}
