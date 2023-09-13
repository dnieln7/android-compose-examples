package soy.gabimoreno.danielnolasco.ui.screen.catlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import soy.gabimoreno.danielnolasco.domain.model.Cat
import soy.gabimoreno.danielnolasco.ui.composable.FullScreenCircularProgress
import soy.gabimoreno.danielnolasco.ui.composable.FullScreenError
import soy.gabimoreno.danielnolasco.ui.composable.ListTileCat
import soy.gabimoreno.danielnolasco.ui.theme.DanielNolascoTheme

@Composable
fun CatListScreen(
    navigateToCatDetail: (String) -> Unit,
    catListViewModel: CatListViewModel = hiltViewModel()
) {
    val state by catListViewModel.catListState.collectAsState()

    LaunchedEffect(key1 = "getCats", block = { catListViewModel.getCats() })

    Scaffold {
        when (state) {
            CatListState.Loading -> FullScreenCircularProgress(
                modifier = Modifier.padding(it),
            )
            is CatListState.Error -> FullScreenError(
                modifier = Modifier.padding(it),
                messageRes = (state as CatListState.Error).messageRes,
                message = (state as CatListState.Error).message,
                onRetry = { catListViewModel.getCats() }
            )
            is CatListState.Success -> CatList(
                modifier = Modifier.padding(it),
                cats = (state as CatListState.Success).data,
                navigateToCatDetail = navigateToCatDetail
            )
        }
    }
}

@Composable
fun CatList(modifier: Modifier, cats: List<Cat>, navigateToCatDetail: (String) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(cats) { cat ->
            Box(modifier = Modifier.padding(4.dp)) {
                ListTileCat(cat = cat, onClick = { navigateToCatDetail(cat.name) })
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CatListScreenPreview() {
    DanielNolascoTheme {
        CatListScreen(navigateToCatDetail = {})
    }
}
