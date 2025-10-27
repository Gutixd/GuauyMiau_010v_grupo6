package com.example.guaumiau.ui.theme.pets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guaumiau.data.local.entity.Pet
import com.example.guaumiau.data.repository.PetRepository
import com.example.guaumiau.domain.model.PetType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PetsViewModel @Inject constructor(
    private val repo: PetRepository
) : ViewModel() {

    private val _userId = MutableStateFlow<Long?>(null)
    val pets = MutableStateFlow<List<Pet>>(emptyList())

    fun observePets(userId: Long) {
        _userId.value = userId
        viewModelScope.launch {
            repo.petsOf(userId).collect { pets.value = it }
        }
    }

    fun addPet(type: PetType?, name: String) = viewModelScope.launch {
        val uid = _userId.value ?: return@launch
        repo.addPet(uid, type, name)
    }

    fun updatePet(pet: Pet) = viewModelScope.launch { repo.updatePet(pet) }
    fun deletePet(pet: Pet) = viewModelScope.launch { repo.deletePet(pet) }
}
