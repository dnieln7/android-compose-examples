package soy.gabimoreno.danielnolasco.domain.usecase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import soy.gabimoreno.danielnolasco.data.database.WalkingDatabase
import soy.gabimoreno.danielnolasco.data.datasource.locationevent.LocalLocationEventDataSource
import soy.gabimoreno.danielnolasco.data.datasource.walkingsession.LocalWalkingSessionDataSource
import soy.gabimoreno.danielnolasco.data.repository.locationevent.DefaultLocationEventRepository
import soy.gabimoreno.danielnolasco.data.repository.walkingsession.DefaultWalkingSessionRepository
import soy.gabimoreno.danielnolasco.fake.buildWalkingSession

@RunWith(AndroidJUnit4::class)
class DeleteWalkingSessionAndRelatedDataUseCaseTest {

    private lateinit var localWalkingSessionDataSource: LocalWalkingSessionDataSource
    private lateinit var localLocationEventDataSource: LocalLocationEventDataSource
    private lateinit var useCase: DeleteWalkingSessionAndRelatedDataUseCase

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()

        val database = Room.inMemoryDatabaseBuilder(
            context,
            WalkingDatabase::class.java
        ).build()

        localWalkingSessionDataSource = LocalWalkingSessionDataSource(
            database.walkingSessionDbModelDao()
        )
        localLocationEventDataSource = LocalLocationEventDataSource(
            database.locationEventDbModelDao()
        )

        val walkingSessionRepository = DefaultWalkingSessionRepository(
            localWalkingSessionDataSource
        )
        val locationEventRepository = DefaultLocationEventRepository(
            localLocationEventDataSource
        )

        useCase = DeleteWalkingSessionAndRelatedDataUseCase(
            walkingSessionRepository,
            locationEventRepository,
        )
    }

    @Test
    fun given_a_valid_startTime_when_invoke_then_delete_walking_sessions_and_related_data() {
        val walkingSession = buildWalkingSession()
        val startTime = walkingSession.startTime

        runBlocking {
            localWalkingSessionDataSource.saveWalkingSession(walkingSession)

            walkingSession.locationEvents.forEach {
                localLocationEventDataSource.saveLocationEvent(it, startTime)
            }

            val result = useCase(startTime)

            result.shouldBeTrue()

            val walkingSessions = localWalkingSessionDataSource.getWalkingSessions()
            val locationEvents = localLocationEventDataSource.getLocationEventsByOwnerStartTime(
                startTime
            )

            walkingSessions.shouldBeEmpty()
            locationEvents.shouldBeEmpty()
        }
    }

    @Test
    fun given_an_invalid_startTime_when_invoke_then_walking_sessions_and_related_data_should_not_be_deleted() {
        val walkingSession = buildWalkingSession()
        val startTime = walkingSession.startTime

        runBlocking {
            localWalkingSessionDataSource.saveWalkingSession(walkingSession)

            walkingSession.locationEvents.forEach {
                localLocationEventDataSource.saveLocationEvent(it, startTime)
            }

            val result = useCase(0)

            result.shouldBeTrue()

            val walkingSessions = localWalkingSessionDataSource.getWalkingSessions()
            val locationEvents = localLocationEventDataSource.getLocationEventsByOwnerStartTime(
                startTime
            )

            walkingSessions.shouldNotBeEmpty()
            locationEvents.shouldNotBeEmpty()
        }
    }
}
