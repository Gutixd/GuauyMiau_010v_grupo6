package com.example.guaumiau.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.guaumiau.data.local.dao.PetDao
import com.example.guaumiau.data.local.dao.UserDao
import com.example.guaumiau.data.local.entity.Pet
import com.example.guaumiau.data.local.entity.User

@Database(
    entities = [User::class, Pet::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun petDao(): PetDao
}
