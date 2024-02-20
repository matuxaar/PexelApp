package com.example.pexelapp.ui.bookmarksscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarksScreenAction
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarksScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarksViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _bookmarksScreenState =
        MutableStateFlow(BookmarksScreenState())
    val bookmarksScreenState = _bookmarksScreenState.asStateFlow()

    fun handleAction(action: BookmarksScreenAction) {
        when (action) {
            is BookmarksScreenAction.Init -> getLikedPhotos()
        }
    }

    fun getLikedPhotos() {
        viewModelScope.launch {
            _bookmarksScreenState.value = _bookmarksScreenState.value.copy(isLoading = true)
            val photos = repository.subscribeToPhotos()
            _bookmarksScreenState.update { currentState ->
                currentState.copy(photoList = photos)
            }
            _bookmarksScreenState.value = _bookmarksScreenState.value.copy(isLoading = false)
        }
    }
}