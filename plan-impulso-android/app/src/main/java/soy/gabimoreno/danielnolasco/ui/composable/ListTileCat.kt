package soy.gabimoreno.danielnolasco.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soy.gabimoreno.danielnolasco.domain.extensions.isPlayful
import soy.gabimoreno.danielnolasco.domain.model.Cat
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListTileCat(cat: Cat, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = cat.name)
                Spacer(modifier = Modifier.height(8.dp))
                Text(style = MaterialTheme.typography.caption, text = cat.origin)
            }
            Spacer(modifier = Modifier.width(16.dp))
            if (cat.isPlayful) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = stringResource(id = R.string.playful),
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListTileCatPreview() {
    DanielNolascoTheme {
        ListTileCat(
            cat = Cat("Abyssinian", "Southeast Asia", 5),
            onClick = {}
        )
    }
}
