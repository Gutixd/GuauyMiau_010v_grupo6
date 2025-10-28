package com.example.guaumiau.ui.theme.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guaumiau.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {

    data class UiState(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onChange(email: String? = null, password: String? = null) {
        _state.value = _state.value.copy(
            email = email ?: _state.value.email,
            password = password ?: _state.value.password
        )
    }

    fun login() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true, error = null)
        val res = authRepo.login(_state.value.email, _state.value.password)
        _state.value = if (res.isSuccess)
            _state.value.copy(isLoading = false)
        else
            _state.value.copy(isLoading = false, error = "Usuario o contraseña incorrectos")
    }
}
