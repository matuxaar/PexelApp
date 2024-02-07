package com.example.pexelapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCuratedPhotos(): Flow<PagingData<Photo>>

    fun getSearchPhotos(query: String): Flow<PagingData<Photo>>

    suspend fun getPhoto(id: Int, isFromBookMark: Boolean): Flow<Photo>

    suspend fun addToBookmarks(photo: Photo)

    fun getAllImagesFromDb(): Flow<PagingData<Photo>>

    fun likePhoto(photoId: Int)
}