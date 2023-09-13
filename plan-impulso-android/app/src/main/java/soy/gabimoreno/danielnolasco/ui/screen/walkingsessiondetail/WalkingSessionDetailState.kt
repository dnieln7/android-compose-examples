package soy.gabimoreno.danielnolasco.ui.screen.walkingsessiondetail

import androidx.annotation.StringRes
import soy.gabimoreno.danielnolasco.domain.model.WalkingSession

sealed class WalkingSessionDetailState {
    object Loading : WalkingSessionDetailState()
    class Error(
        @StringRes val messageRes: Int? = null,
        val message: String? = null,
    ) : WalkingSessionDetailState()

    class Success(val data: WalkingSession) : WalkingSessionDetailState()
}
