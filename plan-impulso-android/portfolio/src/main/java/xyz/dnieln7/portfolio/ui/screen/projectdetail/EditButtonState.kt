package xyz.dnieln7.portfolio.ui.screen.projectdetail

sealed class EditButtonState {
    object Visible : EditButtonState()
    object Invisible : EditButtonState()
}
