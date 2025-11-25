package com.example.guaumiau.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.guaumiau.data.local.dao.PetDao
import com.example.guaumiau.data.local.dao.UserDao
import com.example.guaumiau.data.local.entity.Pet
import com.example.guaumiau.data.local.entity.User

@Database( // esta es la clase principal de la base de datos
    entities = [User::class, Pet::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao // da acceso a la tabla de usuarios
    abstract fun petDao(): PetDao // y este a la de mascotas
}
