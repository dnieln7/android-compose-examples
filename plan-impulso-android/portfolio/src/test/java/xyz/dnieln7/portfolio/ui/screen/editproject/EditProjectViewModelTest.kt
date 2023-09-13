package xyz.dnieln7.portfolio.ui.screen.editproject

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.connectivity.Connectivity
import xyz.dnieln7.core.domain.connectivity.ConnectivityStatus
import xyz.dnieln7.portfolio.domain.usecase.GetProjectByIdUseCase
import xyz.dnieln7.portfolio.domain.usecase.IsUserSessionValidUseCase
import xyz.dnieln7.portfolio.domain.usecase.UpdateProjectLocalUseCase
import xyz.dnieln7.portfolio.domain.usecase.UpdateProjectUseCase
import xyz.dnieln7.portfolio.extensions.awaitItemTest
import xyz.dnieln7.portfolio.extensions.coVerifyOnce
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.fake.buildProject

@ExperimentalCoroutinesApi
class EditProjectViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val connectivity = relaxedMockk<Connectivity>()
    private val isUserSessionValidUseCase = relaxedMockk<IsUserSessionValidUseCase>()
    private val getProjectByIdUseCase = relaxedMockk<GetProjectByIdUseCase>()
    private val updateProjectUseCase = relaxedMockk<UpdateProjectUseCase>()
    private val updateProjectLocalUseCase = relaxedMockk<UpdateProjectLocalUseCase>()

    private lateinit var viewModel: EditProjectViewModel

    @Before
    fun setup() {
        viewModel = EditProjectViewModel(
            dispatcher = dispatcher,
            connectivity = connectivity,
            isUserSessionValidUseCase = isUserSessionValidUseCase,
            getProjectByIdUseCase = getProjectByIdUseCase,
            updateProjectUseCase = updateProjectUseCase,
            updateProjectLocalUseCase = updateProjectLocalUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN uiState should be EditProjectState_None`() {
        runTest(dispatcher) {
            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf EditProjectState.None::class }
        }
    }

    @Test
    fun `GIVEN a Project WHEN getProjectById THEN uiState should be EditProjectState_Success and update should be false`() {
        val project = buildProject()

        coEvery { getProjectByIdUseCase.invoke(any()) } returns project.right()

        runTest(dispatcher) {
            viewModel.getProjectById(0)

            viewModel.uiState.awaitItemTest {
                it shouldBeInstanceOf EditProjectState.Success::class
                (it as EditProjectState.Success).update.shouldBeFalse()
            }
        }
    }

    @Test
    fun `GIVEN a String WHEN getProjectById THEN uiState should be EditProjectState_Error`() {
        coEvery { getProjectByIdUseCase.invoke(any()) } returns "".left()

        runTest(dispatcher) {
            viewModel.getProjectById(0)

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf EditProjectState.Error::class }
        }
    }

    @Test
    fun `GIVEN isUserSessionValidUseCase returns false WHEN updateProject THEN uiState should be EditProjectState_NotAuthenticated`() {
        val project = buildProject()

        every { isUserSessionValidUseCase.invoke() } returns false

        runTest(dispatcher) {
            viewModel.updateProject(project)

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf EditProjectState.NotAuthenticated::class }
        }
    }

    @Test
    fun `GIVEN ConnectivityStatus_CONNECTED WHEN updateProject THEN updateProjectUseCase should be called once`() {
        val project = buildProject()

        every { isUserSessionValidUseCase.invoke() } returns true
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED
        coEvery { updateProjectUseCase.invoke(any()) } returns project.right()

        runTest(dispatcher) {
            viewModel.updateProject(project)

            coVerifyOnce {
                updateProjectUseCase.invoke(project)
            }
        }
    }

    @Test
    fun `GIVEN ConnectivityStatus_DISCONNECTED WHEN getProjects THEN updateProjectInDatabaseUseCase should be called once`() {
        val project = buildProject()

        every { isUserSessionValidUseCase.invoke() } returns true
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.DISCONNECTED
        coEvery { updateProjectLocalUseCase.invoke(any()) } returns project.right()

        runTest(dispatcher) {
            viewModel.updateProject(project)

            coVerifyOnce {
                updateProjectLocalUseCase.invoke(project)
            }
        }
    }

    @Test
    fun `GIVEN a Project WHEN updateProjectUseCase THEN uiState should be EditProjectState_Success and update should be true`() {
        val project = buildProject()
        val newProject = project.copy(description = "new_description")

        every { isUserSessionValidUseCase.invoke() } returns true
        coEvery { connectivity.checkConnectivity() } returns ConnectivityStatus.CONNECTED
        coEvery { updateProjectUseCase.invoke(any()) } returns newProject.right()

        runTest(dispatcher) {
            viewModel.updateProject(newProject)

            viewModel.uiState.awaitItemTest {
                it shouldBeInstanceOf EditProjectState.Success::class
                (it as EditProjectState.Success).update.shouldBeTrue()
            }
        }
    }

    @Test
    fun `GIVEN a String WHEN updateProjectUseCase THEN uiState should be EditProjectState_UpdateError`() {
        val project = buildProject()
        val newProject = project.copy(description = "new_description")

        every { isUserSessionValidUseCase.invoke() } returns true
        coEvery { connectivity.checkConnectivity() } returns ConnectivityStatus.CONNECTED
        coEvery { updateProjectUseCase.invoke(any()) } returns "".left()

        runTest(dispatcher) {
            viewModel.updateProject(newProject)

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf EditProjectState.UpdateError::class }
        }
    }

    @Test
    fun `GIVEN a Project WHEN updateProjectInDatabaseUseCase THEN uiState should be EditProjectState_Success and update should be true`() {
        val project = buildProject()
        val newProject = project.copy(description = "new_description")

        every { isUserSessionValidUseCase.invoke() } returns true
        coEvery { connectivity.checkConnectivity() } returns ConnectivityStatus.DISCONNECTED
        coEvery { updateProjectLocalUseCase.invoke(any()) } returns newProject.right()

        runTest(dispatcher) {
            viewModel.updateProject(newProject)

            viewModel.uiState.awaitItemTest {
                it shouldBeInstanceOf EditProjectState.Success::class
                (it as EditProjectState.Success).update.shouldBeTrue()
            }
        }
    }

    @Test
    fun `GIVEN a String WHEN updateProjectInDatabaseUseCase THEN uiState should be EditProjectState_UpdateError`() {
        val project = buildProject()
        val newProject = project.copy(description = "new_description")

        every { isUserSessionValidUseCase.invoke() } returns true
        coEvery { connectivity.checkConnectivity() } returns ConnectivityStatus.DISCONNECTED
        coEvery { updateProjectLocalUseCase.invoke(any()) } returns "".left()

        runTest(dispatcher) {
            viewModel.updateProject(newProject)

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf EditProjectState.UpdateError::class }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN onProjectUpdated THEN uiState should be EditProjectState_Success and update should be false`() {
        val project = buildProject()

        runTest(dispatcher) {
            viewModel.onProjectUpdated(project)

            viewModel.uiState.awaitItemTest {
                it shouldBeInstanceOf EditProjectState.Success::class
                (it as EditProjectState.Success).update.shouldBeFalse()
            }
        }
    }
}
