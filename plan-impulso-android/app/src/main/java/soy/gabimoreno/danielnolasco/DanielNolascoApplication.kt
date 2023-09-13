package soy.gabimoreno.danielnolasco

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import soy.gabimoreno.danielnolasco.framework.workmanager.DeleteOldWalkingSessionsWorker
import timber.log.Timber

@HiltAndroidApp
class DanielNolascoApplication : Application() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()
        initDebugLogs()

        workManager.enqueueUniquePeriodicWork(
            DeleteOldWalkingSessionsWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            DeleteOldWalkingSessionsWorker.buildRequest(),
        )
    }

    private fun initDebugLogs() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
