package soy.gabimoreno.danielnolasco.ui.screen.catdetail

import androidx.annotation.StringRes
import soy.gabimoreno.danielnolasco.domain.model.Cat

sealed class CatDetailState {
    object Loading : CatDetailState()
    class Error(
        @StringRes val messageRes: Int? = null,
        val message: String? = null
    ) : CatDetailState()
    class Success(val data: Cat) : CatDetailState()
}
