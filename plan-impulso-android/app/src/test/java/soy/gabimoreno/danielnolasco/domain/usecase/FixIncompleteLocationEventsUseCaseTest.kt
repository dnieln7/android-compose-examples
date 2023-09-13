package soy.gabimoreno.danielnolasco.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository
import soy.gabimoreno.danielnolasco.extensions.relaxedMockk
import soy.gabimoreno.danielnolasco.fake.buildLocationEvents
import soy.gabimoreno.danielnolasco.fake.buildLocationEventsWithNullDisplayName

@ExperimentalCoroutinesApi
class FixIncompleteLocationEventsUseCaseTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var useCase: FixIncompleteLocationEventsUseCase

    @Before
    fun setUp() {
        val locationEventRepository = relaxedMockk<LocationEventRepository>()
        val processLocationUseCase = relaxedMockk<ProcessLocationUseCase>()

        useCase = FixIncompleteLocationEventsUseCase(
            locationEventRepository,
            processLocationUseCase,
        )
    }

    @Test
    fun `GIVEN a list of LocationEvent with null displayName WHEN invoke THEN the returned list should not be empty`() {
        runTest(dispatcher) {
            val locationEvents = buildLocationEventsWithNullDisplayName()

            val result = useCase(locationEvents)

            result.shouldNotBeEmpty()
        }
    }

    @Test
    fun `GIVEN a list of LocationEvent with non null displayName WHEN invoke THEN the returned list should be empty`() {
        runTest(dispatcher) {
            val locationEvents = buildLocationEvents()

            val result = useCase(locationEvents)

            result.shouldBeEmpty()
        }
    }

    @Test
    fun `GIVEN an empty list of LocationEvent WHEN invoke THEN the returned list should be empty`() {
        runTest(dispatcher) {
            val locationEvents = emptyList<LocationEvent>()

            val result = useCase(locationEvents)

            result.shouldBeEmpty()
        }
    }
}
