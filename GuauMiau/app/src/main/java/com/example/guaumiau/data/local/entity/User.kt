package com.example.guaumiau.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Representa a un usuario registrado en la aplicación Guau&Miau.
 * Se almacena localmente en la base de datos Room.
 */
@Entity(
    tableName = "users",
    indices = [Index(value = ["email"], unique = true)]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val fullName: String,

    val email: String,

    // Contraseña almacenada como hash (PBKDF2, nunca texto plano)
    val passwordHash: String,

    // Teléfono opcional
    val phone: String? = null,

    // Fecha de registro en milisegundos
    val createdAt: Long = System.currentTimeMillis()
)
