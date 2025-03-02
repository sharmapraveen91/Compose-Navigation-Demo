package com.praveen.picsumapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.praveen.picsumapp.domain.model.PicsumImage
import com.praveen.picsumapp.domain.usecase.GetPicSumImagesUseCase
import com.praveen.picsumapp.domain.utils.ResultState
import com.praveen.picsumapp.presentation.state.PicSumUiEvent
import com.praveen.picsumapp.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Praveen.Sharma on 01/03/25 - 11:22..
 *
 ***/
@HiltViewModel
class PicSumViewModel @Inject constructor(
    private val getPicsumImagesUseCase: GetPicSumImagesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<PicsumImage>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<PicsumImage>>> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<PicSumUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchImages()
    }

    private fun fetchImages() {
        viewModelScope.launch {
            getPicsumImagesUseCase()
                .onStart { _uiState.value = UiState.Loading }
                .catch { exception ->
                    val errorMessage = exception.message ?: "Unknown error"
                    _uiState.value = UiState.Error(errorMessage)
                    _eventFlow.emit(PicSumUiEvent.ShowError(errorMessage))
                }
                .collect { result -> // ✅ Replaced collectLatest with collect
                    when (result) {
                        is ResultState.Success -> _uiState.value = UiState.Success(result.data)
                        is ResultState.Error -> {
                            _uiState.value = UiState.Error(result.message)
                            _eventFlow.emit(PicSumUiEvent.ShowError(result.message))
                        }

                        ResultState.Loading -> _uiState.value = UiState.Loading
                    }
                }
        }
    }
}