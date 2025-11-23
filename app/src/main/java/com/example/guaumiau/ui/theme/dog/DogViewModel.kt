package com.example.guaumiau.ui.theme.dog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guaumiau.data.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DogUiState(
    val isLoading: Boolean = false,
    val imageUrl: String? = null,
    val error: String? = null
)

@HiltViewModel
class DogViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DogUiState())
    val uiState: StateFlow<DogUiState> = _uiState

    fun loadRandomDog() {
        _uiState.value = DogUiState(isLoading = true)

        viewModelScope.launch {
            val result = repository.getRandomDogImageUrl()

            _uiState.value = result.fold(
                onSuccess = { DogUiState(isLoading = false, imageUrl = it) },
                onFailure = { DogUiState(isLoading = false, error = it.message) }
            )
        }
    }
}
