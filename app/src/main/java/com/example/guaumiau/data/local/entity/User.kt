package com.example.guaumiau.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


 // Se almacena localmente en la base de datos Room
 
@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val fullName: String,

    val email: String,

    // Contraseña almacenada  
    val passwordHash: String,

    // Teléfono que es opcional
    val phone: String? = null,

    // Fecha de registro que tiene hasta microsegundos 
    val createdAt: Long = System.currentTimeMillis()
)
