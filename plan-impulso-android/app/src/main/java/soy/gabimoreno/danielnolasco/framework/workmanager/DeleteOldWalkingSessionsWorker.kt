package soy.gabimoreno.danielnolasco.framework.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import soy.gabimoreno.danielnolasco.domain.usecase.DeleteWalkingSessionAndRelatedDataUseCase
import soy.gabimoreno.danielnolasco.domain.usecase.GetWalkingSessionsOlderThan7DaysUseCase

@HiltWorker
class DeleteOldWalkingSessionsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val getWalkingSessionsOlderThan7DaysUseCase: GetWalkingSessionsOlderThan7DaysUseCase,
    private val deleteWalkingSessionAndRelatedDataUseCase: DeleteWalkingSessionAndRelatedDataUseCase,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val result = getWalkingSessionsOlderThan7DaysUseCase()

        if (result.isLeft()) {
            return Result.retry()
        } else {
            val sessions = result.orNull() ?: emptyList()
            var errors = 0

            for (session in sessions) {
                val success = deleteWalkingSessionAndRelatedDataUseCase(session.startTime)

                if (!success) {
                    errors += 1
                }
            }

            return if (errors > 0) Result.retry() else Result.success()
        }
    }

    companion object {

        const val WORK_NAME = "delete_old_walking_sessions"

        fun buildRequest(): PeriodicWorkRequest {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            return PeriodicWorkRequestBuilder<DeleteOldWalkingSessionsWorker>(
                TWELVE_HOURS,
                TimeUnit.HOURS,
            ).addTag(WORK_NAME).setConstraints(constraints).build()
        }
    }
}

private const val TWELVE_HOURS = 12L
