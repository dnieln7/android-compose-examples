package soy.gabimoreno.danielnolasco.framework.connectivity

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.NetworkRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import soy.gabimoreno.danielnolasco.extensions.relaxedMockk
import soy.gabimoreno.danielnolasco.extensions.verifyOnce
import xyz.dnieln7.core.domain.connectivity.Connectivity

@ExperimentalCoroutinesApi
class ConnectivityMonitorTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var connectivityMonitor: ConnectivityMonitor

    @Before
    fun setUp() {
        connectivityManager = relaxedMockk()
        val networkRequest = relaxedMockk<NetworkRequest>()
        val connectivity = Connectivity(dispatcher)

        connectivityMonitor = ConnectivityMonitor(
            connectivityManager,
            networkRequest,
            connectivity,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN connectivityStatus is collected THEN registerNetworkCallback should be called once`() {
        runTest(dispatcher) {
            connectivityMonitor.connectivityStatus.first()

            verifyOnce { connectivityManager.registerNetworkCallback(any(), any<NetworkCallback>()) }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN connectivityStatus collection is canceled THEN unregisterNetworkCallback should be called once`() {
        runTest(dispatcher) {
            connectivityMonitor.connectivityStatus.first()

            verifyOnce { connectivityManager.unregisterNetworkCallback(any<NetworkCallback>()) }
        }
    }
}
