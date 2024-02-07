package com.example.pexelapp.ui.bookmarksscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.domain.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarksViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun getLikedPhotos(): Flow<PagingData<Photo>> =
        repository.getAllImagesFromDb().cachedIn(viewModelScope)
}