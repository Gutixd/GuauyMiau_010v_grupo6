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

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "guau_miau.db").build()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()

    @Provides
    fun providePetDao(db: AppDatabase): PetDao = db.petDao()

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext ctx: Context): SessionManager =
        SessionManager(ctx)
}
