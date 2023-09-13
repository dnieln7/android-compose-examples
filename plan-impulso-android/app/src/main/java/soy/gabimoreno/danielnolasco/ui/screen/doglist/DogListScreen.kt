package soy.gabimoreno.danielnolasco.ui.screen.doglist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.launch
import soy.gabimoreno.danielnolasco.domain.model.Dog
import soy.gabimoreno.danielnolasco.ui.composable.FullScreenCircularProgress
import soy.gabimoreno.danielnolasco.ui.composable.FullScreenError
import soy.gabimoreno.danielnolasco.ui.composable.ListTileDog
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme
import xyz.dnieln7.core.res.R

@Composable
fun DogListScreen(
    dogListViewModel: DogListViewModel = hiltViewModel(),
    navigateDogToDetail: (String) -> Unit
) {
    val state by dogListViewModel.uiState.collectAsState()
    val lazyPagingItems = state.collectAsLazyPagingItems()

    LaunchedEffect(key1 = "getDogs", block = { dogListViewModel.getDogs() })

    Scaffold {
        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            FullScreenCircularProgress(
                modifier = Modifier.padding(it),
            )
        }

        if (lazyPagingItems.loadState.refresh is LoadState.Error) {
            val throwable = (lazyPagingItems.loadState.refresh as LoadState.Error).error

            FullScreenError(
                modifier = Modifier.padding(it),
                messageRes = null,
                message = dogListViewModel.getDogApiServiceError(throwable),
                onRetry = { lazyPagingItems.refresh() }
            )
        }

        if (lazyPagingItems.loadState.refresh is LoadState.NotLoading) {
            DogList(
                modifier = Modifier.padding(it),
                pagingItems = lazyPagingItems,
                navigateDogToDetail = navigateDogToDetail,
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DogList(
    modifier: Modifier,
    pagingItems: LazyPagingItems<Dog>,
    navigateDogToDetail: (String) -> Unit,
) {
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        pagingItems.refresh()
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    Box(Modifier.pullRefresh(refreshState)) {
        LazyColumn(modifier = modifier) {
            items(count = pagingItems.itemCount) { index ->
                pagingItems[index]?.let { dog ->
                    Box(modifier = Modifier.padding(4.dp)) {
                        ListTileDog(dog = dog, onClick = { navigateDogToDetail(dog.name) })
                    }
                }
            }

            if (pagingItems.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }

            if (pagingItems.loadState.append is LoadState.Error) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                    ) {
                        Button(onClick = { pagingItems.retry() }) {
                            Text(text = stringResource(R.string.retry))
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun DogListScreenPreview() {
    DanielNolascoTheme {
        DogListScreen(navigateDogToDetail = {})
    }
}
