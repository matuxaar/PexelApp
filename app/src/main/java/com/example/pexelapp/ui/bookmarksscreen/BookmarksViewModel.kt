package com.example.pexelapp.ui.bookmarksscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarksScreenAction
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarksScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
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

    private fun getLikedPhotos() {
        repository.subscribeToPhotos()
            .cachedIn(viewModelScope)
            .onStart {
                _bookmarksScreenState.value = _bookmarksScreenState.value.copy(isLoading = true)
            }
            .catch {
                it.printStackTrace()
            }
            .onEach {
                _bookmarksScreenState.update { currentState ->
                    currentState.copy(photoList = flowOf(it))
                }
                _bookmarksScreenState.value = _bookmarksScreenState.value.copy(isLoading = false)
            }.launchIn(viewModelScope)
    }
}
