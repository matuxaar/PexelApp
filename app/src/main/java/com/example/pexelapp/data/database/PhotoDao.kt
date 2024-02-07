package com.example.pexelapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo_table WHERE id = :id")
    fun getPhotoById(id: Int): PhotoEntity

    @Query("SELECT * FROM photo_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getAllPhotoFromDb(limit: Int, offset: Int): List<PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBookmarks(photoEntity: PhotoEntity)
}