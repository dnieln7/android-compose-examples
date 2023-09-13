package soy.gabimoreno.danielnolasco.ui.screen.walkinghistory

import soy.gabimoreno.danielnolasco.domain.model.WalkingSession

sealed class WalkingHistoryState {
    object Loading : WalkingHistoryState()
    class Error(val message: String? = null) : WalkingHistoryState()
    class Success(val data: List<WalkingSession>) : WalkingHistoryState()
}
