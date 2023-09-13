package xyz.dnieln7.portfolio.ui.screen.projectdetail

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.core.ui.UiState
import xyz.dnieln7.portfolio.domain.usecase.GetProjectByIdUseCase
import xyz.dnieln7.portfolio.domain.usecase.IsUserSessionValidUseCase
import xyz.dnieln7.portfolio.extensions.awaitItemTest
import xyz.dnieln7.portfolio.extensions.relaxedMockk
import xyz.dnieln7.portfolio.fake.buildProject

@ExperimentalCoroutinesApi
class ProjectDetailViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val isUserSessionValidUseCase = relaxedMockk<IsUserSessionValidUseCase>()
    private val getProjectByIdUseCase = relaxedMockk<GetProjectByIdUseCase>()

    private lateinit var viewModel: ProjectDetailViewModel

    @Before
    fun setup() {
        viewModel = ProjectDetailViewModel(
            dispatcher = dispatcher,
            isUserSessionValidUseCase = isUserSessionValidUseCase,
            getProjectByIdUseCase = getProjectByIdUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN uiState should be UiState_None`() {
        runTest(dispatcher) {
            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf UiState.None::class }
        }
    }

    @Test
    fun `GIVEN true WHEN updateEditButtonState THEN editButtonState should be EditButtonState_Visible`() {
        every { isUserSessionValidUseCase.invoke() } returns true

        runTest(dispatcher) {
            viewModel.updateEditButtonState()

            viewModel.editButtonState.awaitItemTest { it shouldBeInstanceOf EditButtonState.Visible::class }
        }
    }

    @Test
    fun `GIVEN false WHEN updateEditButtonState THEN editButtonState should be EditButtonState_Invisible`() {
        every { isUserSessionValidUseCase.invoke() } returns false

        runTest(dispatcher) {
            viewModel.updateEditButtonState()

            viewModel.editButtonState.awaitItemTest { it shouldBeInstanceOf EditButtonState.Invisible::class }
        }
    }

    @Test
    fun `GIVEN a Project WHEN getProjectById THEN uiState should be UiState_Success`() {
        val project = buildProject()

        coEvery { getProjectByIdUseCase.invoke(any()) } returns project.right()

        runTest(dispatcher) {
            viewModel.getProjectById(0)

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf UiState.Success::class }
        }
    }

    @Test
    fun `GIVEN a String WHEN getProjectById THEN uiState should be UiState_Error`() {
        coEvery { getProjectByIdUseCase.invoke(any()) } returns "".left()

        runTest(dispatcher) {
            viewModel.getProjectById(0)

            viewModel.uiState.awaitItemTest { it shouldBeInstanceOf UiState.Error::class }
        }
    }
}
