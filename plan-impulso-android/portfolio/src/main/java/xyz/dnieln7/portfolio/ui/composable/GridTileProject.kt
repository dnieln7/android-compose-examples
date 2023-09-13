package xyz.dnieln7.portfolio.ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import xyz.dnieln7.core.res.R
import xyz.dnieln7.core.ui.composable.SwipeToDeleteBackground
import xyz.dnieln7.portfolio.domain.model.Project

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun LazyGridItemScope.SwipeGridTileProject(
    modifier: Modifier = Modifier,
    project: Project,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismiss(
        modifier = modifier.animateItemPlacement(),
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(SWIPE_TRIGGER_FRACTION) },
        background = { SwipeToDeleteBackground(modifier, dismissState) }
    ) {
        GridTileProject(modifier.fillMaxSize(), project, onClick)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GridTileProject(
    modifier: Modifier = Modifier,
    project: Project,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxSize(),
        elevation = 0.dp,
        shape = RectangleShape,
        onClick = onClick,
    ) {
        Column(modifier) {
            AsyncImage(
                modifier = modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                model = project.thumbnail,
                contentDescription = stringResource(R.string.project_thumbnail),
                error = painterResource(
                    xyz.dnieln7.portfolio.R.drawable.ic_broken_image
                ),
            )
            Column(modifier.padding(vertical = 12.dp, horizontal = 4.dp)) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = project.name,
                    style = MaterialTheme.typography.subtitle1,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = project.summary,
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}

private const val SWIPE_TRIGGER_FRACTION = 0.75F
