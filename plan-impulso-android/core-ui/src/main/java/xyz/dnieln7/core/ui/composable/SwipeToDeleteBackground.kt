package xyz.dnieln7.core.ui.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.dnieln7.core.res.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDeleteBackground(
    modifier: Modifier = Modifier,
    dismissState: DismissState,
) {
    if (dismissState.dismissDirection == null) {
        return
    }

    Card(
        modifier = modifier.fillMaxSize(),
        backgroundColor = Color.Red,
        elevation = 0.dp,
    ) {
        Icon(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            painter = painterResource(R.drawable.ic_delete_sweep),
            contentDescription = stringResource(R.string.delete)
        )
    }
}
