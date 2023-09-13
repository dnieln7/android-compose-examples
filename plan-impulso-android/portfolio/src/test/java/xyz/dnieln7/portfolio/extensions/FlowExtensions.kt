package xyz.dnieln7.portfolio.extensions

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow

suspend fun <T> Flow<T>.awaitItemTest(block: suspend (T) -> Unit) {
    test {
        val item = awaitItem()

        block(item)
    }
}
