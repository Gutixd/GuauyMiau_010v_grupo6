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

    @Insert
    suspend fun insert(pet: Pet): Long

    @Update
    suspend fun update(pet: Pet)

    @Delete
    suspend fun delete(pet: Pet)

    @Query("SELECT * FROM pets WHERE userId = :userId ORDER BY name")
    fun petsOf(userId: Long): Flow<List<Pet>>
}
