package soy.gabimoreno.danielnolasco.data.repository.walkingsession

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import soy.gabimoreno.danielnolasco.data.database.WalkingDatabase
import soy.gabimoreno.danielnolasco.data.datasource.walkingsession.LocalWalkingSessionDataSource
import soy.gabimoreno.danielnolasco.fake.buildWalkingSession

@RunWith(AndroidJUnit4::class)
class DefaultWalkingSessionRepositoryTest {

    private lateinit var database: WalkingDatabase
    private lateinit var repository: DefaultWalkingSessionRepository

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()

        database = Room.inMemoryDatabaseBuilder(
            context,
            WalkingDatabase::class.java
        ).build()

        repository = DefaultWalkingSessionRepository(
            LocalWalkingSessionDataSource(database.walkingSessionDbModelDao())
        )
    }

    @Test
    fun given_an_existing_WalkingSession_when_getWalkingSessions_then_returned_list_should_not_be_empty() {
        val walkingSession = buildWalkingSession()

        runBlocking {
            repository.saveWalkingSession(walkingSession)

            val walkingSessions = repository.getWalkingSessions().orNull() ?: emptyList()

            walkingSessions.shouldNotBeEmpty()
        }
    }

    @Test
    fun given_a_WalkingSession_from_yesterday_when_getWalkingSessionsWithStartTimeOlderThan_then_returned_list_should_not_be_empty() {
        val todayMillis = DateTime.now(DateTimeZone.UTC).millis
        val walkingSession = buildWalkingSession(1)

        runBlocking {
            repository.saveWalkingSession(walkingSession)

            val walkingSessions = repository.getWalkingSessionsWithStartTimeOlderThan(
                todayMillis
            ).orNull() ?: emptyList()

            walkingSessions.shouldNotBeEmpty()
        }
    }

    @Test
    fun given_an_existing_WalkingSession_when_getWalkingSessionByStartTime_then_returned_object_should_not_be_null() {
        val walkingSession = buildWalkingSession()

        runBlocking {
            repository.saveWalkingSession(walkingSession)

            val walkingSessions = repository.getWalkingSessionByStartTime(
                walkingSession.startTime
            ).orNull()

            walkingSessions.shouldNotBeNull()
        }
    }

    @Test
    fun given_the_happy_path_when_updateWalkingSessionDuration_then_returned_object_should_be_updated() {
        val durationToUpdate = 1000L
        val walkingSession = buildWalkingSession()

        runBlocking {
            repository.saveWalkingSession(walkingSession)
            repository.updateWalkingSessionDuration(walkingSession.startTime, durationToUpdate)

            val newDuration = repository.getWalkingSessionByStartTime(
                walkingSession.startTime
            ).orNull()?.duration ?: 0L

            newDuration shouldBeEqualTo durationToUpdate
        }
    }

    @Test
    fun given_the_happy_path_when_updateWalkingSessionAsFinished_then_returned_object_should_be_finished() {
        val walkingSession = buildWalkingSession()

        runBlocking {
            repository.saveWalkingSession(walkingSession)
            repository.updateWalkingSessionAsFinished(walkingSession.startTime, 1000L)

            val newWalkingSession = repository.getWalkingSessionByStartTime(
                walkingSession.startTime
            ).orNull()

            newWalkingSession?.endTime.shouldNotBeNull()
        }
    }

    @Test
    fun given_the_happy_path_when_deleteWalkingSessionsByStartTime_then_returned_list_should_be_empty() {
        val walkingSession = buildWalkingSession()

        runBlocking {
            repository.saveWalkingSession(walkingSession)
            repository.deleteWalkingSessionsByStartTime(walkingSession.startTime)

            val walkingSessions = repository.getWalkingSessions().orNull()

            walkingSessions.shouldNotBeNull()
            walkingSessions.shouldBeEmpty()
        }
    }

    @After
    fun teardown() {
        database.close()
    }
}
