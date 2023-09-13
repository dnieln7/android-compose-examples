package soy.gabimoreno.danielnolasco.ui.screen.dogdetail

import androidx.annotation.StringRes
import soy.gabimoreno.danielnolasco.domain.model.Dog

sealed class DogDetailState {
    object Loading : DogDetailState()
    class Error(
        @StringRes val messageRes: Int? = null,
        val message: String? = null
    ) : DogDetailState()
    class Success(val data: Dog) : DogDetailState()
}
