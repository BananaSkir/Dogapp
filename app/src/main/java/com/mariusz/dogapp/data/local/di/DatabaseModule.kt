package com.mariusz.dogapp.data.local.di

import android.content.Context
import androidx.room.Room
import com.mariusz.dogapp.data.local.database.AppDatabase
import com.mariusz.dogapp.data.local.database.DogEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideDogDao(appDatabase: AppDatabase): DogEntityDao {
        return appDatabase.DogDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "DogsDB"
        ).build()
    }
}