package soy.gabimoreno.danielnolasco.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.RemoteMediator
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.danielnolasco.data.datasource.dog.LocalDogDataSource
import soy.gabimoreno.danielnolasco.data.datasource.dog.RemoteDogDataSource
import soy.gabimoreno.danielnolasco.domain.model.Dog
import soy.gabimoreno.danielnolasco.domain.provider.PreferencesProvider
import soy.gabimoreno.danielnolasco.domain.usecase.RefreshDogsFromApiUseCase
import soy.gabimoreno.danielnolasco.extensions.coVerifyNever
import soy.gabimoreno.danielnolasco.extensions.relaxedMockk
import soy.gabimoreno.danielnolasco.fake.buildDog
import soy.gabimoreno.danielnolasco.fake.buildPagingState

@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoroutinesApi
class DogRemoteMediatorTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val remoteDogDataSource = relaxedMockk<RemoteDogDataSource>()
    private val localDogDataSource = relaxedMockk<LocalDogDataSource>()
    private val preferencesProvider = relaxedMockk<PreferencesProvider>()
    private val refreshDogsFromApiUseCase = relaxedMockk<RefreshDogsFromApiUseCase>()

    private lateinit var mediator: DogRemoteMediator

    @Before
    fun setup() {
        mediator = DogRemoteMediator(
            remoteDogDataSource = remoteDogDataSource,
            localDogDataSource = localDogDataSource,
            preferencesProvider = preferencesProvider,
            refreshDogsFromApiUseCase = refreshDogsFromApiUseCase,
        )
    }

    @Test
    fun `GIVEN refreshDogsFromApiUseCase = true WHEN initialize THEN return LAUNCH_INITIAL_REFRESH`() {
        coEvery { refreshDogsFromApiUseCase(any(), any()) } returns true
        coEvery { localDogDataSource.isEmpty() } returns true

        runTest(dispatcher) {
            val result = mediator.initialize()

            result shouldBeInstanceOf RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH::class
        }
    }

    @Test
    fun `GIVEN localDogDataSource_isEmpty = true WHEN initialize THEN return LAUNCH_INITIAL_REFRESH`() {
        coEvery { refreshDogsFromApiUseCase(any(), any()) } returns false
        coEvery { localDogDataSource.isEmpty() } returns true

        runTest(dispatcher) {
            val result = mediator.initialize()

            result shouldBeInstanceOf RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH::class
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN initialize THEN return SKIP_INITIAL_REFRESH`() {
        coEvery { refreshDogsFromApiUseCase(any(), any()) } returns false
        coEvery { localDogDataSource.isEmpty() } returns false

        runTest(dispatcher) {
            val result = mediator.initialize()

            result shouldBeInstanceOf RemoteMediator.InitializeAction.SKIP_INITIAL_REFRESH::class
        }
    }

    @Test
    fun `GIVEN a List of Dog WHEN load THEN return Success(false)`() {
        val dog = buildDog()

        coEvery { remoteDogDataSource.getDogs(any()) } returns listOf(dog).right()

        runTest(dispatcher) {
            val result = mediator.load(LoadType.REFRESH, buildPagingState(20))

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached.shouldBeFalse()
        }
    }

    @Test
    fun `GIVEN an empty List of Dog data WHEN load THEN return Success(true)`() {
        coEvery { remoteDogDataSource.getDogs(any()) } returns emptyList<Dog>().right()

        runTest(dispatcher) {
            val result = mediator.load(LoadType.REFRESH, buildPagingState(20))

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached.shouldBeTrue()
        }
    }

    @Test
    fun `GIVEN a Throwable WHEN load THEN return Error`() {
        val errorMessage = "Error message"
        val exception = Exception(errorMessage)

        coEvery { remoteDogDataSource.getDogs(any()) } returns exception.left()

        runTest(dispatcher) {
            val result = mediator.load(LoadType.REFRESH, buildPagingState(20))

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Error::class
            (result as RemoteMediator.MediatorResult.Error).throwable.message shouldBeEqualTo errorMessage
        }
    }

    @Test
    fun `GIVEN PREPPEND WHEN load THEN only return Success(true)`() {
        runTest(dispatcher) {
            val result = mediator.load(LoadType.PREPEND, buildPagingState(20))

            coVerifyNever {
                preferencesProvider.saveDogsLastOffset(any())
            }

            result shouldBeInstanceOf RemoteMediator.MediatorResult.Success::class
            (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached.shouldBeTrue()
        }
    }

    @Test
    fun `GIVEN APPEND WHEN load THEN call the expected functions`() {
        val offset = 20

        every { preferencesProvider.getDogsLastOffset() } returns offset
        coEvery { remoteDogDataSource.getDogs(any()) } returns emptyList<Dog>().right()

        runTest(dispatcher) {
            mediator.load(LoadType.APPEND, buildPagingState(20))

            coVerifyOrder {
                preferencesProvider.getDogsLastOffset()
                preferencesProvider.saveDogsLastOffset(offset.plus(DOGS_PAGE_SIZE))
            }
        }
    }
}
