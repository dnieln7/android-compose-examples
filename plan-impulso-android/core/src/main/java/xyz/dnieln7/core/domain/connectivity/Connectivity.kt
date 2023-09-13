package xyz.dnieln7.core.domain.connectivity

import java.io.IOException
import java.net.InetAddress
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import xyz.dnieln7.core.di.IO

@Singleton
class Connectivity @Inject constructor(@IO private val dispatcher: CoroutineDispatcher) {

    suspend fun checkConnectivity(timeout: Int = MINIMUM_TIMEOUT): ConnectivityStatus =
        withContext(dispatcher) {
            if (timeout < MINIMUM_TIMEOUT) {
                throw TimeoutTooShortException()
            }

            val reachable = try {
                InetAddress.getByName("google.com").isReachable(timeout)
            } catch (exception: IOException) {
                Timber.e(exception, "Error checking connection")
                false
            }

            return@withContext if (reachable) {
                ConnectivityStatus.CONNECTED
            } else {
                ConnectivityStatus.DISCONNECTED
            }
        }
}

private const val MINIMUM_TIMEOUT = 1000
