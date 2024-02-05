package xyz.dnieln7.canvascourse.common.composable.custom.weightpicker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeightPicker() {
    var weight by remember { mutableIntStateOf(INITIAL_WEIGHT) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Pick your weight", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "$weight KG", style = MaterialTheme.typography.displaySmall)
        Spacer(modifier = Modifier.height(20.dp))
        ScaleLegacy(
            modifier = Modifier,
            style = ScaleStyle(),
            minWeight = 0,
            maxWeight = 150,
            initialWeight = INITIAL_WEIGHT,
            onWeightChanged = { weight = it },
        )
    }
}

private const val INITIAL_WEIGHT = 75
