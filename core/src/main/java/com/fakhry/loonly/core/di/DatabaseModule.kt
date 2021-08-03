package com.fakhry.loonly.core.di

import android.content.Context
import androidx.room.Room
import com.fakhry.loonly.core.data.source.local.room.LoonlyDao
import com.fakhry.loonly.core.data.source.local.room.LoonlyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): LoonlyDatabase =
        Room.databaseBuilder(
            context,
            LoonlyDatabase::class.java, "Loonly.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideLoonlyDao(database: LoonlyDatabase): LoonlyDao =
        database.loonlyDao()
}