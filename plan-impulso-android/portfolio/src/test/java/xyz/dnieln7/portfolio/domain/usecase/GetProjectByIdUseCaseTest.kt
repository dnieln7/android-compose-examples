package xyz.dnieln7.portfolio.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.provider.ResourceProvider
import xyz.dnieln7.core.domain.usecase.GetErrorFromThrowableUseCase
import xyz.dnieln7.portfolio.domain.repository.ProjectRepository
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.fake.buildProject

@ExperimentalCoroutinesApi
class GetProjectByIdUseCaseTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val resourceProvider = relaxedMockk<ResourceProvider>()
    private val projectRepository = relaxedMockk<ProjectRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetProjectByIdUseCase

    @Before
    fun setup() {
        useCase = GetProjectByIdUseCase(
            resourceProvider = resourceProvider,
            projectRepository = projectRepository,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN a non null project WHEN invoke THEN return the expected Project`() {
        val project = buildProject()

        coEvery { projectRepository.getProjectById(any()) } returns project.right()

        runTest(dispatcher) {
            val result = useCase(project.id).orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo project
        }
    }

    @Test
    fun `GIVEN a null project WHEN invoke THEN return not found message`() {
        val errorMessage = "Not found"

        coEvery { projectRepository.getProjectById(any()) } returns null.right()
        every { resourceProvider.getString(any()) } returns errorMessage

        runTest(dispatcher) {
            val result = useCase(0).swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo errorMessage
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN invoke THEN return the expected message`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)

        coEvery { projectRepository.getProjectById(any()) } returns exception.left()
        every { getErrorFromThrowableUseCase.invoke(any()) } returns errorMessage

        runTest(dispatcher) {
            val result = useCase(0).swap().orNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo errorMessage
        }
    }
}
