package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import javax.inject.Inject
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.core.res.R
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository

class GetProjectByIdUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val projectRepository: ProjectRepository,
    private val getErrorFromThrowableUseCase: GetErrorFromThrowableUseCase,
) {

    suspend operator fun invoke(id: Int): Either<String, Project> {
        return projectRepository.getProjectById(id).fold(
            {
                getErrorFromThrowableUseCase(it).left()
            },
            {
                it?.right() ?: resourceProvider.getString(R.string.not_found).left()
            }
        )
    }
}
