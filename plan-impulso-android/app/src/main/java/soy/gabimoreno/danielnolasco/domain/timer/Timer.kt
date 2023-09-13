package soy.gabimoreno.danielnolasco.domain.timer

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import xyz.dnieln7.core.di.IO

class Timer @Inject constructor(@IO dispatcher: CoroutineDispatcher) {

    private val scope = CoroutineScope(SupervisorJob() + dispatcher)
    private var job: Job? = null

    private var started = false
    private var totalSeconds = 0L

    private val _seconds = MutableSharedFlow<Long>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val seconds = _seconds.asSharedFlow()

    fun start() {
        if (!started) {
            started = true

            attemptToStopJob()

            job = scope.launch {
                while (isActive) {
                    totalSeconds += 1
                    _seconds.emit(totalSeconds)
                    delay(ONE_SECOND)
                }
            }
        }
    }

    fun stop() {
        started = false
        attemptToStopJob()
    }

    private fun attemptToStopJob() {
        job?.cancel()
        job = null
    }
}

private const val ONE_SECOND = 1_000L
