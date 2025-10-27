package com.example.guaumiau.data.repository

import com.example.guaumiau.data.local.dao.PetDao
import com.example.guaumiau.data.local.entity.Pet
import com.example.guaumiau.domain.model.PetType
import com.example.guaumiau.domain.validation.ValidationError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PetRepository @Inject constructor(
    private val petDao: PetDao
) {
    fun petsOf(userId: Long): Flow<List<Pet>> = petDao.petsOf(userId)

    suspend fun addPet(userId: Long, type: PetType?, name: String): Result<Unit> {
        val errors = buildList {
            if (type == null) add(ValidationError.PetTypeMissing)
            if (name.isBlank()) add(ValidationError.PetNameEmpty)
            else if (name.length > 50) add(ValidationError.PetNameTooLong)
        }
        if (errors.isNotEmpty()) return Result.failure(IllegalArgumentException(errors.joinToString()))
        petDao.insert(Pet(userId = userId, type = type!!, name = name.trim()))
        return Result.success(Unit)
    }

    suspend fun updatePet(pet: Pet) = petDao.update(pet)
    suspend fun deletePet(pet: Pet) = petDao.delete(pet)
}
