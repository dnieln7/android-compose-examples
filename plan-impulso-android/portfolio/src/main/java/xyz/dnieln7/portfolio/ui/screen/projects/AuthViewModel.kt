package xyz.dnieln7.portfolio.ui.screen.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.core.di.IO
import xyz.dnieln7.portfolio.domain.usecase.IsUserSessionValidUseCase
import xyz.dnieln7.portfolio.domain.usecase.LoginUseCase
import xyz.dnieln7.portfolio.domain.usecase.LogoutUseCase

@HiltViewModel
class AuthViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val isUserSessionValidUseCase: IsUserSessionValidUseCase,
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.LoggedOut)
    val uiState get() = _uiState.asStateFlow()

    fun checkUserSession() {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(AuthState.Loading)

            if (isUserSessionValidUseCase()) {
                _uiState.emit(AuthState.LoggedIn)
            } else {
                _uiState.emit(AuthState.LoggedOut)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(AuthState.Loading)

            loginUseCase(email, password).fold(
                {
                    _uiState.emit(AuthState.LoginError(it))
                },
                {
                    _uiState.emit(AuthState.LoggedIn)
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(AuthState.Loading)
            logoutUseCase()
            _uiState.emit(AuthState.LoggedOut)
        }
    }

    fun onLoginErrorShown() {
        viewModelScope.launch(dispatcher) {
            _uiState.emit(AuthState.LoggedOut)
        }
    }
}
