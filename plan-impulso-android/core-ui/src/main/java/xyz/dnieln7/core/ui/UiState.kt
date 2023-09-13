package xyz.dnieln7.core.ui

sealed class UiState<out T> {
    object None : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    class Error(val message: String) : UiState<Nothing>()
    class Success<T>(val data: T) : UiState<T>()
}
