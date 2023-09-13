package xyz.dnieln7.core.ui.composable

import androidx.compose.material.MaterialTheme
import app.cash.paparazzi.DeviceConfig.Companion.PIXEL_5
import app.cash.paparazzi.Paparazzi
import org.junit.Rule
import org.junit.Test

class FullScreenErrorTest {

    @get:Rule
    val paparazzi = Paparazzi(
        deviceConfig = PIXEL_5,
    )

    @Test
    fun `GIVEN the happy path WHEN FullScreenError THEN display the expected UI`() {
        paparazzi.snapshot {
            MaterialTheme() {
                FullScreenError(message = "There was an error", onRetryMessage = "Retry", onRetry = {})
            }
        }
    }
}
