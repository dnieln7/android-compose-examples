package xyz.dnieln7.portfolio.ui.screen.projects

sealed class AuthState {
    object Loading : AuthState()
    object LoggedIn : AuthState()
    object LoggedOut : AuthState()
    object LogoutError : AuthState()
    class LoginError(val message: String) : AuthState()
}
