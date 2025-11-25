package com.example.guaumiau.di

import android.content.Context
import androidx.room.Room
import com.example.guaumiau.data.local.AppDatabase
import com.example.guaumiau.data.local.dao.PetDao
import com.example.guaumiau.data.local.dao.UserDao
import com.example.guaumiau.data.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //Provee la base de datos Room como instancia única

    @Provides
    @Singleton // provee la base de datos
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "guau_miau.db").build()

    @Provides //provee UserDao
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides //lo provee PetDao
    fun providePetDao(db: AppDatabase): PetDao = db.petDao()

    @Provides
    @Singleton //Provee el administrador de sesión
    fun provideSessionManager(@ApplicationContext ctx: Context): SessionManager =
        SessionManager(ctx)
}
