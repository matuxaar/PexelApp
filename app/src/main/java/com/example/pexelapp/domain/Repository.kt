package com.example.pexelapp.domain

import android.content.Context
import androidx.paging.PagingData
import com.example.pexelapp.domain.model.FeaturedCollection
import com.example.pexelapp.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCuratedPhotos(page: Int): Result<List<Photo>>

    suspend fun getSearchPhotos(page: Int, query: String): Result<List<Photo>>

    fun getPhoto(id: Int): Flow<Photo>

    fun getPhotoFromDb(id: Int): Flow<Photo>

    suspend fun addToBookmarks(photo: Photo)

    suspend fun removeFromBookmarks(photoId: Int)

    fun getAllImagesFromDb(): Flow<PagingData<Photo>>

    suspend fun savePhotoToDevice(context: Context, imageUrl: String, photoId: Int)

    suspend fun isPhotoDownloaded(context: Context, photoId: Int): Boolean

    suspend fun getCollections(): List<FeaturedCollection>

    suspend fun getLikeState(photoId: Int): Boolean

    suspend fun saveLikeState(photo: Photo)

    suspend fun getPhotosFromDb(): List<Photo>
}