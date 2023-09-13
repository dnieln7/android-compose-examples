package soy.gabimoreno.danielnolasco.framework.connectivity

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkRequest
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import xyz.dnieln7.core.domain.connectivity.Connectivity

@OptIn(ExperimentalCoroutinesApi::class)
@Singleton
class ConnectivityMonitor @Inject constructor(
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest,
    private val connectivity: Connectivity,
) {

    val connectivityStatus
        get() = callbackFlow {
            val networkCallback = object : NetworkCallback() {

                override fun onAvailable(network: Network) {
                    launch { send(true) }
                }

                override fun onUnavailable() {
                    launch { send(false) }
                }

                override fun onLost(network: Network) {
                    launch { send(false) }
                }
            }

            launch { send(false) }

            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

            awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
        }.distinctUntilChanged().mapLatest {
            val connectivityStatus = connectivity.checkConnectivity()

            connectivityStatus
        }
}
