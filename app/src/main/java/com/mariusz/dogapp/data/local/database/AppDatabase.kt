package com.mariusz.dogapp.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DogEntity::class], version = 2, autoMigrations = [
    AutoMigration(from = 1, to = 2)
], exportSchema = true)
abstract class AppDatabase: RoomDatabase() {
    abstract fun DogDao(): DogEntityDao
}