package soy.gabimoreno.danielnolasco.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@Composable
fun ButtonIcon(
    @DrawableRes icon: Int,
    @StringRes label: Int,
    left: Boolean = true,
    right: Boolean = false,
    onClick: () -> Unit,
) {
    Button(onClick = onClick) {
        if (left) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = stringResource(label))
        if (right) {
            Icon(
                painter = painterResource(icon),
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonIconPreview() {
    DanielNolascoTheme {
        ButtonIcon(
            icon = soy.gabimoreno.danielnolasco.R.drawable.ic_cat,
            label = R.string.cats,
            onClick = {}
        )
    }
}
