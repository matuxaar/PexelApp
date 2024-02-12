package com.example.pexelapp.data.source

import com.example.pexelapp.data.database.PhotoDao
import com.example.pexelapp.data.database.PhotoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataBaseSource @Inject constructor(
    private val photoDao: PhotoDao
) {

    suspend fun addToBookmarks(photoEntity: PhotoEntity) = photoDao.addToBookmarks(photoEntity)

    fun getPhotoById(id: Int): Flow<PhotoEntity> = photoDao.getPhotoById(id)

    suspend fun getAllPhotoFromDb(limit: Int, offset: Int): List<PhotoEntity> =
        photoDao.getAllPhotoFromDb(limit, offset)

    suspend fun removeFromBookmark(photoId: Int) = photoDao.removeFromBookmark(photoId)

    suspend fun getLikeState(photoId: Int): Boolean = photoDao.getLikeState(photoId)

    suspend fun saveLikeState(photoEntity: PhotoEntity) =
        photoDao.saveLikeState(photoEntity)

}