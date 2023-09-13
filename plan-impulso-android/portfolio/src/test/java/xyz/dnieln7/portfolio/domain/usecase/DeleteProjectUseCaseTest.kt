package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.fake.buildProject

@ExperimentalCoroutinesApi
class DeleteProjectUseCaseTest {

    private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()

    private val projectRepository = relaxedMockk<ProjectRepository>()
    private val getPortfolioApiServiceErrorUseCase =
        relaxedMockk<GetPortfolioApiServiceErrorUseCase>()

    private lateinit var useCase: DeleteProjectUseCase

    @Before
    fun setup() {
        useCase = DeleteProjectUseCase(projectRepository, getPortfolioApiServiceErrorUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return Unit`() {
        val project = buildProject()

        coEvery { projectRepository.deleteProject(any(), true) } returns Unit.right()

        runTest(dispatcher) {
            val result = useCase(project, true).orNull()

            result.shouldNotBeNull()
            result shouldBeInstanceOf Unit::class
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN invoke THEN return the expected message`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)
        val project = buildProject()

        coEvery { projectRepository.deleteProject(any(), true) } returns exception.left()
        every { getPortfolioApiServiceErrorUseCase(any()) } returns errorMessage

        runTest(dispatcher) {
            val result = useCase(project, true).swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo errorMessage
        }
    }
}
