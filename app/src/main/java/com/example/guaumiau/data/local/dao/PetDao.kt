package com.example.guaumiau.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.guaumiau.data.local.entity.Pet
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
// tenemos el dao para las operaciones relacionadas
    @Insert //inserta el animal en la base de datos
    suspend fun insert(pet: Pet): Long

    @Update // aqui lo actualiza
    suspend fun update(pet: Pet)

    @Delete // lo elimina pero no esta implementado en la app
    suspend fun delete(pet: Pet)

    @Query("SELECT * FROM pets WHERE userId = :userId ORDER BY name")
    fun petsOf(userId: Long): Flow<List<Pet>>// aqui aparecen todas las mascotas que son de un usuario id
}
