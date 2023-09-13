package soy.gabimoreno.danielnolasco.ui.screen.catlist

import androidx.annotation.StringRes
import soy.gabimoreno.danielnolasco.domain.model.Cat

sealed class CatListState {
    object Loading : CatListState()
    class Error(
        @StringRes val messageRes: Int? = null,
        val message: String? = null
    ) : CatListState()
    class Success(val data: List<Cat>) : CatListState()
}
