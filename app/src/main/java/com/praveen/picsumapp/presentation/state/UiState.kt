package com.praveen.picsumapp.presentation.state


sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

sealed class PicsumAction {
    object LoadImages : PicsumAction()
}

sealed class PicSumUiEvent {
    data class ShowError(val message: String) : PicSumUiEvent()
}