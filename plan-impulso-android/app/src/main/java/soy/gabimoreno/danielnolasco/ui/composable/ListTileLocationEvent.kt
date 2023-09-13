package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soy.gabimoreno.danielnolasco.domain.model.LocationEvent
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@Composable
fun ListTileLocationEvent(locationEvent: LocationEvent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(soy.gabimoreno.danielnolasco.R.drawable.ic_pin),
                contentDescription = stringResource(R.string.location_event),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                if (locationEvent.displayName != null) {
                    Text(
                        text = locationEvent.displayName,
                        style = MaterialTheme.typography.body1,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(
                        R.string.lat_placeholder_lng_placeholder,
                        locationEvent.latitude,
                        locationEvent.longitude,
                    ),
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListTileLocationEventPreview() {
    DanielNolascoTheme {
        ListTileLocationEvent(locationEvent = LocationEvent(1.0, 2.0, "displayName"))
    }
}
