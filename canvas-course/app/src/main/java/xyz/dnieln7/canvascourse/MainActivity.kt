package xyz.dnieln7.canvascourse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import xyz.dnieln7.canvascourse.common.composable.custom.weightpicker.WeightPicker
import xyz.dnieln7.canvascourse.common.composable.theme.CVTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CVTheme(darkTheme = false, dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeightPicker()
                }
            }
        }
    }
}
