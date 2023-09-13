package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.provider.DateProvider
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository
import xyz.dnieln7.portfolio.extensions.coVerifyOnce
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.fake.buildProject

@ExperimentalCoroutinesApi
class UpdateProjectUseCaseTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val projectRepository = relaxedMockk<ProjectRepository>()
    private val getPortfolioApiServiceErrorUseCase =
        relaxedMockk<GetPortfolioApiServiceErrorUseCase>()

    private lateinit var useCase: UpdateProjectUseCase

    @Before
    fun setup() {
        val dateProvider = DateProvider()

        useCase = UpdateProjectUseCase(
            dateProvider = dateProvider,
            projectRepository = projectRepository,
            getPortfolioApiServiceErrorUseCase = getPortfolioApiServiceErrorUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN updateProject(true) should be called once`() {
        val project = buildProject()
        val updatedProject = project.copy(updatedAt = "new_updated_at")

        coEvery { projectRepository.updateProject(any(), true) } returns updatedProject.right()

        runTest(dispatcher) {
            useCase(project)

            coVerifyOnce {
                projectRepository.updateProject(any(), true)
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return a Project with a new updatedAt`() {
        val project = buildProject()
        val updatedProject = project.copy(updatedAt = "new_updated_at")

        coEvery { projectRepository.updateProject(any(), any()) } returns updatedProject.right()

        runTest(dispatcher) {
            val result = useCase(project).orNull()

            result.shouldNotBeNull()

            with(result) {
                id shouldBeEqualTo project.id
                name shouldBeEqualTo project.name
                ownership shouldBeEqualTo project.ownership
                summary shouldBeEqualTo project.summary
                year shouldBeEqualTo project.year
                importance shouldBeEqualTo project.importance
                thumbnail shouldBeEqualTo project.thumbnail
                images shouldBeEqualTo project.images
                tags shouldBeEqualTo project.tags
                duration shouldBeEqualTo project.duration
                description shouldBeEqualTo project.description
                features shouldBeEqualTo project.features
                technologies shouldBeEqualTo project.technologies
                androidGit shouldBeEqualTo project.androidGit
                androidUrl shouldBeEqualTo project.androidUrl
                webUrl shouldBeEqualTo project.webUrl
                webGit shouldBeEqualTo project.webGit
                programUrl shouldBeEqualTo project.programUrl
                programGit shouldBeEqualTo project.programGit
                createdAt shouldBeEqualTo project.createdAt
                updatedAt shouldNotBeEqualTo project.updatedAt
            }
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN invoke THEN return the expected message`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)
        val project = buildProject()

        coEvery { projectRepository.updateProject(any(), any()) } returns exception.left()
        every { getPortfolioApiServiceErrorUseCase.invoke(any()) } returns errorMessage

        runTest(dispatcher) {
            val result = useCase(project).swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo errorMessage
        }
    }
}
