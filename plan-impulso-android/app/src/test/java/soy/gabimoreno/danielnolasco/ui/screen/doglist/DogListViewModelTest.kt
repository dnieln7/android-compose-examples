package soy.gabimoreno.danielnolasco.ui.screen.doglist

import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.danielnolasco.domain.repository.dog.DogRepository
import soy.gabimoreno.danielnolasco.domain.usecase.GetDogApiServiceErrorUseCase
import soy.gabimoreno.danielnolasco.extensions.coVerifyNever
import soy.gabimoreno.danielnolasco.extensions.coVerifyOnce
import soy.gabimoreno.danielnolasco.extensions.relaxedMockk
import xyz.dnieln7.core.domain.connectivity.Connectivity
import xyz.dnieln7.core.domain.connectivity.ConnectivityStatus

@ExperimentalCoroutinesApi
class DogListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val dogRepository = relaxedMockk<DogRepository>()
    private val getDogApiServiceErrorUseCase = relaxedMockk<GetDogApiServiceErrorUseCase>()
    private val connectivity = relaxedMockk<Connectivity>()

    private lateinit var viewModel: DogListViewModel

    @Before
    fun setup() {
        viewModel = DogListViewModel(
            dogRepository = dogRepository,
            getDogApiServiceErrorUseCase = getDogApiServiceErrorUseCase,
            connectivity = connectivity,
            dispatcher = dispatcher,
        )
    }

    @Test
    fun `GIVEN ConnectivityStatus_CONNECTED WHEN getDogs THEN call the expected functions`() {
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.CONNECTED

        runTest(dispatcher) {
            viewModel.getDogs()

            coVerifyOnce {
                dogRepository.getDogs()
            }

            coVerifyNever {
                dogRepository.getLocalDogs()
            }
        }
    }

    @Test
    fun `GIVEN ConnectivityStatus_DISCONNECTED WHEN getDogs THEN call the expected functions`() {
        coEvery { connectivity.checkConnectivity(any()) } returns ConnectivityStatus.DISCONNECTED

        runTest(dispatcher) {
            viewModel.getDogs()

            coVerifyOnce {
                dogRepository.getLocalDogs()
            }

            coVerifyNever {
                dogRepository.getDogs()
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN getDogs THEN DogRepository_getLocalDogs should be called once`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)

        runTest(dispatcher) {
            viewModel.getDogApiServiceError(exception)

            coVerifyOnce {
                getDogApiServiceErrorUseCase(exception)
            }
        }
    }
}
