package com.example.pexelapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCuratedPhotos(): Flow<PagingData<Photo>>

    fun getSearchPhotos(query: String): Flow<PagingData<Photo>>
}