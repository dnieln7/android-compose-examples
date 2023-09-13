package xyz.dnieln7.core.ui.composable

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xyz.dnieln7.core.res.R

@Composable
fun ButtonNavigationBack(modifier: Modifier = Modifier, navigateBack: () -> Unit) {
    IconButton(modifier = modifier, onClick = navigateBack) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(id = R.string.go_back)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonNavigationBackPreview() {
    MaterialTheme {
        ButtonNavigationBack(navigateBack = {})
    }
}
