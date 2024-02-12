package com.example.pexelapp.ui.bookmarksscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarkScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarksViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _photoListStateFlow =
        MutableStateFlow<Flow<PagingData<Photo>>>(emptyFlow())
    val photoListStateFlow = _photoListStateFlow.asStateFlow()

    private val _bookmarksScreenState =
        MutableStateFlow(BookmarkScreenState())
    val bookmarksScreenState = _bookmarksScreenState.asStateFlow()


    fun getLikedPhotos() {
        viewModelScope.launch {
            _bookmarksScreenState.value = _bookmarksScreenState.value.copy(isLoading = true)
            _photoListStateFlow.value = repository.getAllImagesFromDb()
//            if (repository.getPhotosFromDb().isEmpty()) {
//                _bookmarksScreenState.value = _bookmarksScreenState.value.copy(isError = true)
//            }
            _bookmarksScreenState.value = _bookmarksScreenState.value.copy(isLoading = false)
        }
    }


}