package xyz.dnieln7.portfolio.ui.screen.projects

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContainSame
import org.amshove.kluent.shouldNotBeNull
import org.amshove.kluent.shouldNotContain
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.domain.connectivity.Connectivity
import xyz.dnieln7.core.domain.connectivity.ConnectivityStatus
import xyz.dnieln7.portfolio.domain.model.Project
import xyz.dnieln7.portfolio.domain.usecase.DeleteProjectUseCase
import xyz.dnieln7.portfolio.domain.usecase.GetProjectsUseCase
import xyz.dnieln7.portfolio.domain.usecase.IsUserSessionValidUseCase
import xyz.dnieln7.portfolio.domain.usecase.SyncProjectsUseCase
import xyz.dnieln7.portfolio.extensions.awaitItemTest
import xyz.dnieln7.portfolio.extensions.coVerifyNever
import xyz.dnieln7.portfolio.extensions.coVerifyOnce
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.fake.buildProject

@ExperimentalCoroutinesApi
class ProjectsViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val connectivity = relaxedMockk<Connectivity>()
    private val syncProjectsUseCase = relaxedMockk<SyncProjectsUseCase>()
    private val getProjectsUseCase = relaxedMockk<GetProjectsUseCase>()
    private val isUserSessionValidUseCase = relaxedMockk<IsUserSessionValidUseCase>()
    private val deleteProjectUseCase = relaxedMockk<DeleteProjectUseCase>()

    private lateinit var viewModel: ProjectsViewModel

    @Before
    fun setup() {
        viewModel = ProjectsViewModel(
            dispatcher,
            connectivity,
            syncProjectsUseCase,
            getProjectsUseCase,
            isUserSessionValidUseCase,
            deleteProjectUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN uiState should have default values`() {
        runTest(dispatcher) {
            viewModel.uiState.awaitItemTest { it shouldBeEqualTo ProjectsState() }
        }
    }

    @Test
    fun `GIVEN connectivity_checkConnectivity returns ConnectivityStatus_CONNECTED WHEN getProjects THEN syncProjectsUseCase_invoke should be called once`() {
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED
        coEvery { syncProjectsUseCase() } returns emptyList<Project>().right()

        runTest(dispatcher) {
            viewModel.getProjects()

            coVerifyOnce {
                syncProjectsUseCase()
            }
        }
    }

    @Test
    fun `GIVEN connectivity_checkConnectivity returns ConnectivityStatus_DISCONNECTED WHEN getProjects THEN getProjectsUseCase_invoke should be called once`() {
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.DISCONNECTED
        coEvery { getProjectsUseCase() } returns emptyList<Project>().right()

        runTest(dispatcher) {
            viewModel.getProjects()

            coVerifyOnce {
                getProjectsUseCase()
            }
        }
    }

    @Test
    fun `GIVEN a List(Project) WHEN getProjects (get) THEN uiState data should contain the expected data`() {
        val project1 = buildProject(id = 1)
        val project2 = buildProject(id = 2)
        val projects = listOf(project1, project2)

        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.DISCONNECTED
        coEvery { getProjectsUseCase() } returns projects.right()

        runTest(dispatcher) {
            viewModel.getProjects()

            viewModel.uiState.awaitItemTest {
                val data = it.data

                data.shouldNotBeNull()
                data shouldContainSame projects
            }
        }
    }

    @Test
    fun `GIVEN a String WHEN getProjects (get) THEN uiState error should return the expected message`() {
        val errorMessage = "Error message"

        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.DISCONNECTED
        coEvery { getProjectsUseCase() } returns errorMessage.left()

        runTest(dispatcher) {
            viewModel.getProjects()

            viewModel.uiState.awaitItemTest {
                val error = it.error

                error.shouldNotBeNull()
                error shouldBeEqualTo errorMessage
            }
        }
    }

    @Test
    fun `GIVEN a List(Project) WHEN getProjects (sync) THEN uiState data should contain the expected data`() {
        val project1 = buildProject(id = 1)
        val project2 = buildProject(id = 2)
        val projects = listOf(project1, project2)

        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED
        coEvery { syncProjectsUseCase() } returns projects.right()

        runTest(dispatcher) {
            viewModel.getProjects()

            viewModel.uiState.awaitItemTest {
                val data = it.data

                data.shouldNotBeNull()
                data shouldContainSame projects
            }
        }
    }

    @Test
    fun `GIVEN a String WHEN getProjects (sync) THEN uiState error should return the expected message`() {
        val errorMessage = "Error message"

        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED
        coEvery { syncProjectsUseCase() } returns errorMessage.left()

        runTest(dispatcher) {
            viewModel.getProjects()

            viewModel.uiState.awaitItemTest {
                val error = it.error

                error.shouldNotBeNull()
                error shouldBeEqualTo errorMessage
            }
        }
    }

    @Test
    fun `GIVEN connectivity_checkConnectivity returns ConnectivityStatus_DISCONNECTED WHEN refreshProjects THEN syncProjectsUseCase_invoke should never be called`() {
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.DISCONNECTED

        runTest(dispatcher) {
            viewModel.refreshProjects()

            coVerifyNever {
                syncProjectsUseCase()
            }
        }
    }

    @Test
    fun `GIVEN a List(Project) WHEN refreshProjects THEN uiState data should contain the expected data`() {
        val project1 = buildProject(id = 1)
        val project2 = buildProject(id = 2)
        val projects = listOf(project1, project2)

        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED
        coEvery { syncProjectsUseCase() } returns projects.right()

        runTest(dispatcher) {
            viewModel.refreshProjects()

            viewModel.uiState.awaitItemTest {
                val data = it.data

                data.shouldNotBeNull()
                data shouldContainSame projects
            }
        }
    }

    @Test
    fun `GIVEN a String WHEN refreshProjects THEN uiState error should return the expected message`() {
        val errorMessage = "Error message"

        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED
        coEvery { syncProjectsUseCase() } returns errorMessage.left()

        runTest(dispatcher) {
            viewModel.refreshProjects()

            viewModel.uiState.awaitItemTest {
                val error = it.error

                error.shouldNotBeNull()
                error shouldBeEqualTo errorMessage
            }
        }
    }

    @Test
    fun `GIVEN isUserSessionValidUseCase returns false WHEN deleteProject THEN deleteProjectUseCase should never be called`() {
        val project = buildProject()

        every { isUserSessionValidUseCase() } returns false

        runTest(dispatcher) {
            viewModel.deleteProject(project)

            coVerifyNever {
                deleteProjectUseCase(project, any())
            }
        }
    }

    @Test
    fun `GIVEN isUserSessionValidUseCase returns false WHEN deleteProject THEN uiState deleteBlocked should be true`() {
        val project = buildProject()

        every { isUserSessionValidUseCase() } returns false

        runTest(dispatcher) {
            viewModel.deleteProject(project)

            viewModel.uiState.awaitItemTest { it.deleteBlocked.shouldBeTrue() }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN deleteProject (remote) THEN uiState deleted should return the deleted project and data should not contain the deleted project`() {
        val project1 = buildProject(id = 1)
        val project2 = buildProject(id = 2)
        val projects = listOf(project1, project2)

        coEvery { syncProjectsUseCase() } returns projects.right()

        every { isUserSessionValidUseCase() } returns true
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED
        coEvery { deleteProjectUseCase(any(), true) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.getProjects()

            viewModel.deleteProject(project1)

            viewModel.uiState.awaitItemTest {
                val deleted = it.deleted
                val data = it.data

                deleted.shouldNotBeNull()
                deleted shouldBeEqualTo project1

                data.shouldNotBeNull()
                data shouldNotContain project1
            }
        }
    }

    @Test
    fun `GIVEN a String WHEN deleteProject (remote) THEN uiState deleteError should return the expected message`() {
        val errorMessage = "Error message"

        val project1 = buildProject(id = 1)
        val project2 = buildProject(id = 2)
        val projects = listOf(project1, project2)

        coEvery { syncProjectsUseCase() } returns projects.right()

        every { isUserSessionValidUseCase() } returns true
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED
        coEvery { deleteProjectUseCase(any(), true) } returns errorMessage.left()

        runTest(dispatcher) {
            viewModel.getProjects()

            viewModel.deleteProject(project1)

            viewModel.uiState.awaitItemTest {
                val deleteError = it.deleteError

                deleteError.shouldNotBeNull()
                deleteError shouldBeEqualTo errorMessage
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN deleteProject (local) THEN uiState deleted should return the deleted project and data should not contain the deleted project`() {
        val project1 = buildProject(id = 1)
        val project2 = buildProject(id = 2)
        val projects = listOf(project1, project2)

        coEvery { getProjectsUseCase() } returns projects.right()

        every { isUserSessionValidUseCase() } returns true
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.DISCONNECTED
        coEvery { deleteProjectUseCase(any(), false) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.getProjects()

            viewModel.deleteProject(project1)

            viewModel.uiState.awaitItemTest {
                val deleted = it.deleted
                val data = it.data

                deleted.shouldNotBeNull()
                deleted shouldBeEqualTo project1

                data.shouldNotBeNull()
                data shouldNotContain project1
            }
        }
    }

    @Test
    fun `GIVEN a String WHEN deleteProject (local) THEN uiState deleteError should return the expected message`() {
        val errorMessage = "Error message"

        val project1 = buildProject(id = 1)
        val project2 = buildProject(id = 2)
        val projects = listOf(project1, project2)

        coEvery { getProjectsUseCase() } returns projects.right()

        every { isUserSessionValidUseCase() } returns true
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.DISCONNECTED
        coEvery { deleteProjectUseCase(any(), false) } returns errorMessage.left()

        runTest(dispatcher) {
            viewModel.getProjects()

            viewModel.deleteProject(project1)

            viewModel.uiState.awaitItemTest {
                val deleteError = it.deleteError

                deleteError.shouldNotBeNull()
                deleteError shouldBeEqualTo errorMessage
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN resetDeleteState THEN uiState deleted, deleteError and deleteBlocked should be reset`() {
        viewModel.resetDeleteState()

        runTest(dispatcher) {
            viewModel.uiState.awaitItemTest {
                it.deleted.shouldBeNull()
                it.deleteError.shouldBeNull()
                it.deleteBlocked.shouldBeFalse()
            }
        }
    }
}
