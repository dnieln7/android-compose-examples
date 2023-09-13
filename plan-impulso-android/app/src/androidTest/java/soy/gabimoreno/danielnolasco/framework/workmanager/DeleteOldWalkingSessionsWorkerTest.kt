package soy.gabimoreno.danielnolasco.framework.workmanager

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.*
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import soy.gabimoreno.danielnolasco.data.database.WalkingDatabase
import soy.gabimoreno.danielnolasco.data.datasource.locationevent.LocalLocationEventDataSource
import soy.gabimoreno.danielnolasco.data.datasource.walkingsession.LocalWalkingSessionDataSource
import soy.gabimoreno.danielnolasco.data.repository.locationevent.DefaultLocationEventRepository
import soy.gabimoreno.danielnolasco.data.repository.walkingsession.DefaultWalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.usecase.DeleteWalkingSessionAndRelatedDataUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.GetWalkingSessionsOlderThan7DaysUseCase
import soy.gabimoreno.danielnolasco.fake.buildWalkingSession

@RunWith(AndroidJUnit4::class)
class DeleteOldWalkingSessionsWorkerTest {

    private lateinit var localWalkingSessionDataSource: LocalWalkingSessionDataSource
    private lateinit var localLocationEventDataSource: LocalLocationEventDataSource

    private lateinit var worker: DeleteOldWalkingSessionsWorker

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

        val getWalkingSessionsOlderThan7DaysUseCase = GetWalkingSessionsOlderThan7DaysUseCase(
            walkingSessionRepository,
        )
        val deleteWalkingSessionAndRelatedDataUseCase = DeleteWalkingSessionAndRelatedDataUseCase(
            walkingSessionRepository,
            locationEventRepository,
        )

        val workerFactory = object : WorkerFactory() {
            override fun createWorker(
                appContext: Context,
                workerClassName: String,
                workerParameters: WorkerParameters
            ): ListenableWorker {
                return DeleteOldWalkingSessionsWorker(
                    context,
                    workerParameters,
                    getWalkingSessionsOlderThan7DaysUseCase,
                    deleteWalkingSessionAndRelatedDataUseCase,
                )
            }
        }

        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, configuration)

        worker = TestListenableWorkerBuilder<DeleteOldWalkingSessionsWorker>(context)
            .setWorkerFactory(workerFactory)
            .setTags(listOf(DeleteOldWalkingSessionsWorker.WORK_NAME))
            .build()
    }

    @Test
    @Throws(Exception::class)
    fun given_walking_sessions_older_than_7_days_when_do_work_then_success() {
        val walkingSession = buildWalkingSession(10)
        val startTime = walkingSession.startTime

        runBlocking {
            localWalkingSessionDataSource.saveWalkingSession(walkingSession)

            walkingSession.locationEvents.forEach {
                localLocationEventDataSource.saveLocationEvent(it, startTime)
            }

            val result = worker.doWork()

            result shouldBeEqualTo ListenableWorker.Result.success()
        }
    }

    @Test
    @Throws(Exception::class)
    fun given_walking_sessions_not_older_than_7_days_when_do_work_then_success() {
        val walkingSession = buildWalkingSession()
        val startTime = walkingSession.startTime

        runBlocking {
            localWalkingSessionDataSource.saveWalkingSession(walkingSession)

            walkingSession.locationEvents.forEach {
                localLocationEventDataSource.saveLocationEvent(it, startTime)
            }

            val result = worker.doWork()

            result shouldBeEqualTo ListenableWorker.Result.success()
        }
    }
}
