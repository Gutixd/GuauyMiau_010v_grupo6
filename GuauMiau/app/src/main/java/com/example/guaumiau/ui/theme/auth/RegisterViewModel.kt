package com.example.guaumiau.ui.theme.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guaumiau.data.repository.AuthRepository
import com.example.guaumiau.data.repository.PetRepository
import com.example.guaumiau.domain.model.PetType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val petRepo: PetRepository
) : ViewModel() {

    data class PetRow(var type: PetType? = null, var name: String = "")
    data class UiState(
        val fullName: String = "",
        val email: String = "",
        val password: String = "",
        val confirm: String = "",
        val phone: String = "",
        val pets: List<PetRow> = listOf(PetRow()),
        val isSubmitting: Boolean = false,
        val error: String? = null,
        val success: Boolean = false
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onChange(
        fullName: String? = null,
        email: String? = null,
        password: String? = null,
        confirm: String? = null,
        phone: String? = null
    ) {
        _state.value = _state.value.copy(
            fullName = fullName ?: _state.value.fullName,
            email = email ?: _state.value.email,
            password = password ?: _state.value.password,
            confirm = confirm ?: _state.value.confirm,
            phone = phone ?: _state.value.phone,
        )
    }

    fun addPetRow() { _state.value = _state.value.copy(pets = _state.value.pets + PetRow()) }
    fun removePetRow(index: Int) {
        _state.value = _state.value.copy(
            pets = _state.value.pets.toMutableList().also { if (index in it.indices) it.removeAt(index) }
        )
    }
    fun setPetType(index: Int, type: PetType) {
        _state.value = _state.value.copy(
            pets = _state.value.pets.toMutableList().also { it[index] = it[index].copy(type = type) }
        )
    }
    fun setPetName(index: Int, name: String) {
        _state.value = _state.value.copy(
            pets = _state.value.pets.toMutableList().also { it[index] = it[index].copy(name = name) }
        )
    }

    fun submit(currentUserId: Long?) = viewModelScope.launch {
        val s = _state.value
        if (s.password != s.confirm) {
            _state.value = s.copy(error = "La confirmación no coincide")
            return@launch
        }
        _state.value = s.copy(isSubmitting = true, error = null)
        val reg = authRepo.register(s.fullName, s.email, s.password, s.phone.ifBlank { null })
        if (reg.isFailure) {
            _state.value = _state.value.copy(isSubmitting = false, error = reg.exceptionOrNull()?.message)
            return@launch
        }
        // Normalmente agregarías mascotas después de tener userId (post-login).
        if (currentUserId != null) {
            s.pets.filter { it.type != null && it.name.isNotBlank() }.forEach {
                petRepo.addPet(currentUserId, it.type, it.name)
            }
        }
        _state.value = _state.value.copy(isSubmitting = false, success = true)
    }
}
