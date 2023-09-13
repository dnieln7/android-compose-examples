package soy.gabimoreno.danielnolasco.domain.usecase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import soy.gabimoreno.danielnolasco.data.database.WalkingDatabase
import soy.gabimoreno.danielnolasco.data.datasource.walkingsession.LocalWalkingSessionDataSource
import soy.gabimoreno.danielnolasco.data.repository.walkingsession.DefaultWalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.fake.buildWalkingSession

@RunWith(AndroidJUnit4::class)
class GetWalkingSessionsOlderThan7DaysUseCaseTest {

    private lateinit var walkingSessionRepository: WalkingSessionRepository
    private lateinit var useCase: GetWalkingSessionsOlderThan7DaysUseCase

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()

        val database = Room.inMemoryDatabaseBuilder(
            context,
            WalkingDatabase::class.java
        ).build()

        val localWalkingSessionDataSource = LocalWalkingSessionDataSource(
            database.walkingSessionDbModelDao()
        )

        walkingSessionRepository = DefaultWalkingSessionRepository(localWalkingSessionDataSource)
        useCase = GetWalkingSessionsOlderThan7DaysUseCase(walkingSessionRepository)
    }

    @Test
    fun given_no_walking_sessions_older_than_7_days_when_invoke_then_return_empty_list() {
        val walkingSessionToday = buildWalkingSession()
        val walkingSessionYesterday = buildWalkingSession(1)

        runBlocking {
            walkingSessionRepository.saveWalkingSession(walkingSessionToday)
            walkingSessionRepository.saveWalkingSession(walkingSessionYesterday)

            val result = useCase().orNull()

            result shouldNotBe null
            result?.shouldBeEmpty()
        }
    }

    @Test
    fun given_walking_sessions_older_than_7_days_when_invoke_then_return_corresponding_walking_sessions() {
        val walkingSessionYesterday = buildWalkingSession(1)
        val walkingSession8DaysOld = buildWalkingSession(8)
        val walkingSession10DaysOld = buildWalkingSession(10)

        runBlocking {
            walkingSessionRepository.saveWalkingSession(walkingSessionYesterday)
            walkingSessionRepository.saveWalkingSession(walkingSession8DaysOld)
            walkingSessionRepository.saveWalkingSession(walkingSession10DaysOld)

            val result = useCase().orNull()

            result shouldNotBe null
            result?.size shouldBeEqualTo 2
        }
    }
}
