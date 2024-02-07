package com.example.pexelapp.data.source

import com.example.pexelapp.data.database.PhotoDao
import com.example.pexelapp.data.database.PhotoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataBaseSource @Inject constructor(
    private val photoDao: PhotoDao
) {

    suspend fun addToBookmarks(photoEntity: PhotoEntity) = photoDao.addToBookmarks(photoEntity)

    fun getPhotoById(id: Int): PhotoEntity = photoDao.getPhotoById(id)

    fun getAllPhotoFromDb(limit: Int, offset: Int): List<PhotoEntity> =
        photoDao.getAllPhotoFromDb(limit, offset)
}