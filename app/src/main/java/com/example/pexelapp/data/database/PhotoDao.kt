package com.example.pexelapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo_table WHERE id = :id")
    fun getPhotoById(id: Int): Flow<PhotoEntity>

    @Query("SELECT * FROM photo_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getAllPhotoFromDb(limit: Int, offset: Int): List<PhotoEntity>

    @Query("SELECT * FROM photo_table")
    suspend fun getAllPhotos(): List<PhotoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBookmarks(photoEntity: PhotoEntity)

    @Query("DELETE FROM photo_table WHERE id = :photoId")
    suspend fun removeFromBookmark(photoId: Int)

}