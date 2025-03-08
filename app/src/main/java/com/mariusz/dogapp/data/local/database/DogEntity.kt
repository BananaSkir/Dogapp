package com.mariusz.dogapp.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "dogs")
data class DogEntity(
    val name: String,

    @ColumnInfo(defaultValue = "0")
    val isFav: Boolean
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}

@Dao
interface DogEntityDao{
    @Query("SELECT * FROM dogs")
    fun getAllDogs(): Flow<List<DogEntity>>

    @Query("SELECT * FROM dogs ORDER BY uid DESC LIMIT 10")
    fun getSortedDogs(): Flow<List<DogEntity>>

    @Query("SELECT * FROM dogs WHERE isFav = 1")
    fun getAllFavDogs(): Flow<List<DogEntity>>

    @Query("UPDATE dogs SET isFav = NOT isFav WHERE uid = :id")
    suspend fun triggerFavDog(id: Int)

    @Insert
    suspend fun insertDog(dog: DogEntity)

    @Query("DELETE FROM dogs WHERE uid = :id")
    suspend fun removeDog(id: Int)
}