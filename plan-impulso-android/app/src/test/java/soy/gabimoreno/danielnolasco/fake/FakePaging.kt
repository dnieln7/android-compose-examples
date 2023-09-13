package soy.gabimoreno.danielnolasco.fake

import androidx.paging.PagingConfig
import androidx.paging.PagingState

fun <K : Any, V : Any> buildPagingState(pageSize: Int): PagingState<K, V> {
    return PagingState(
        pages = listOf(),
        anchorPosition = null,
        config = PagingConfig(
            pageSize = pageSize,
        ),
        leadingPlaceholderCount = 0,
    )
}
