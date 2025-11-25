package com.example.guaumiau.data.repository

import com.example.guaumiau.data.local.dao.PetDao
import com.example.guaumiau.data.local.entity.Pet
import com.example.guaumiau.domain.model.PetType
import com.example.guaumiau.domain.validation.ValidationError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
/**
 * repositorio que gestiona mascotas:
 * lectura, creacion, actualización y eliminacion
 */
class PetRepository @Inject constructor(
    private val petDao: PetDao
) {
    fun petsOf(userId: Long): Flow<List<Pet>> = petDao.petsOf(userId)
// Agrega una nueva mascota después de validar campos
    suspend fun addPet(userId: Long, type: PetType?, name: String): Result<Unit> {
        val errors = buildList {
            if (type == null) add(ValidationError.PetTypeMissing)
            if (name.isBlank()) add(ValidationError.PetNameEmpty)
            else if (name.length > 50) add(ValidationError.PetNameTooLong)
        }
        if (errors.isNotEmpty()) {
            return Result.failure(IllegalArgumentException(errors.joinToString()))
        }

    // aqui se inserta el room
        petDao.insert(
            Pet(
                userId = userId,
                type = type!!,
                name = name.trim()
            )
        )
        return Result.success(Unit)
    }

    // actualizamos una mascota q exista
    suspend fun updatePet(pet: Pet) = petDao.update(pet)

    // y con este eliminamos una mascota
    suspend fun deletePet(pet: Pet) = petDao.delete(pet)
}
