package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository
import xyz.dnieln7.portfolio.extensions.coVerifyOnce
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.fake.buildProject

@ExperimentalCoroutinesApi
class GetProjectsUseCaseTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: GetProjectsUseCase
    private val projectRepository = relaxedMockk<ProjectRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    @Before
    fun setUp() {
        useCase = GetProjectsUseCase(projectRepository, getErrorFromThrowableUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN projectRepository_getProjects(false) should be called once`() {
        val projects = listOf(buildProject())

        coEvery { projectRepository.getProjects(false) } returns projects.right()

        runTest(dispatcher) {
            useCase()

            coVerifyOnce {
                projectRepository.getProjects(false)
            }
        }
    }

    @Test
    fun `GIVEN a list WHEN invoke THEN return the same list`() {
        val projects = listOf(buildProject())

        coEvery { projectRepository.getProjects(any()) } returns projects.right()

        runTest(dispatcher) {
            val result = useCase().orNull()

            result.shouldNotBeNull()
            result shouldContainSame projects
        }
    }

    @Test
    fun `GIVEN a throwable WHEN invoke THEN return the exception message`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)

        coEvery { projectRepository.getProjects(any()) } returns exception.left()
        every { getErrorFromThrowableUseCase.invoke(any()) } returns errorMessage

        runTest(dispatcher) {
            val result = useCase().swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo errorMessage
        }
    }
}
