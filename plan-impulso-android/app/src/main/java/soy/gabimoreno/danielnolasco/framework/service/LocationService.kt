package soy.gabimoreno.danielnolasco.framework.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession
import soy.gabimoreno.danielnolasco.domain.repository.locationevent.LocationEventRepository
import soy.gabimoreno.danielnolasco.domain.repository.walkingsession.WalkingSessionRepository
import soy.gabimoreno.danielnolasco.domain.timer.Timer
import soy.gabimoreno.danielnolasco.domain.usecase.ProcessLocationUseCase
import soy.gabimoreno.danielnolasco.framework.location.FusedLocationProvider
import soy.gabimoreno.danielnolasco.framework.location.UpdatedLocation
import soy.gabimoreno.danielnolasco.framework.notification.LOCATION_SERVICE_NOTIFICATION_ID
import soy.gabimoreno.danielnolasco.framework.notification.NotificationManager

@AndroidEntryPoint
class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private var startTime: Long = DEFAULT_START_TIME

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var timer: Timer

    @Inject
    lateinit var fusedLocationProvider: FusedLocationProvider

    @Inject
    lateinit var walkingSessionRepository: WalkingSessionRepository

    @Inject
    lateinit var locationEventRepository: LocationEventRepository

    @Inject
    lateinit var processLocationUseCase: ProcessLocationUseCase

    override fun onCreate() {
        super.onCreate()
        startListeners()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startTime = intent?.getLongExtra(
            EXTRA_START_TIME,
            DEFAULT_START_TIME
        ) ?: DEFAULT_START_TIME

        serviceScope.launch {
            val walkingSession = WalkingSession(
                startTime = startTime,
                endTime = null,
                duration = 0,
                locationEvents = emptyList()
            )

            walkingSessionRepository.saveWalkingSession(walkingSession)

            timer.start()
            fusedLocationProvider.startLocationUpdates()
        }

        startForeground(
            LOCATION_SERVICE_NOTIFICATION_ID,
            notificationManager.buildLocationServiceNotification()
        )

        sendBroadcast(Intent(ACTION_STARTED))

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        timer.stop()
        fusedLocationProvider.stopLocationUpdates()
    }

    private fun startListeners() {
        serviceScope.launch {
            timer.seconds.collectLatest { onTimerTick(it) }
        }

        serviceScope.launch {
            fusedLocationProvider.locationUpdates.collect { onLocationUpdated(it) }
        }
    }

    private suspend fun onTimerTick(seconds: Long) {
        walkingSessionRepository.updateWalkingSessionDuration(startTime, seconds)

        val intent = Intent(ACTION_TIMER_TICK).apply {
            putExtra(EXTRA_TIMER_SECONDS, seconds)
        }

        sendBroadcast(intent)
    }

    private suspend fun onLocationUpdated(updatedLocation: UpdatedLocation) {
        if (updatedLocation is UpdatedLocation.NewLocation && startTime != DEFAULT_START_TIME) {
            val locationEvent = processLocationUseCase(
                updatedLocation.latitude,
                updatedLocation.longitude,
            )

            locationEventRepository.saveLocationEvent(locationEvent, startTime)
        }
    }

    companion object {
        const val ACTION_STARTED = "framework.service.LocationService.STARTED"
        const val ACTION_TIMER_TICK = "framework.service.LocationService.TIMER_TICK"

        const val EXTRA_START_TIME = "framework.service.LocationService.START_TIME"
        const val EXTRA_TIMER_SECONDS = "framework.service.LocationService.TIMER_SECONDS"

        const val DEFAULT_START_TIME = -1L
    }
}
