package com.example.pexelapp.ui.detailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenAction
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

//    private val _photoStateFlow: MutableStateFlow<Photo> = MutableStateFlow(Photo())
//    val photoStateFlow: StateFlow<Photo> get() = _photoStateFlow

    private val _detailsStateFlow = MutableStateFlow(DetailsScreenState())
    val detailsStateFlow = _detailsStateFlow.asStateFlow()

//    private val _isLiked: MutableStateFlow<Boolean> = MutableStateFlow(false)
//    val isLiked: StateFlow<Boolean> get() = _isLiked

    fun handleAction(action: DetailsScreenAction, isFromBookmarks: Boolean) {
        when (action) {
            is DetailsScreenAction.Init -> init(action.photoId, isFromBookmarks)
            is DetailsScreenAction.Like -> addToBookmarks()
            DetailsScreenAction.BackPress -> backPress()
        }
    }

    private fun init(photoId: Int, isFromBookmarks: Boolean) {

        viewModelScope.launch {
            repository.getPhoto(photoId, isFromBookmarks).onEach { newPhoto ->
                _detailsStateFlow.update { it.copy(photo = newPhoto) }
            }
        }
        //getPhoto(photoId, isFromBookmarks)
    }

    private fun backPress() {

    }

    fun getPhoto(photoId: Int, isFromBookmarks: Boolean) {
        viewModelScope.launch {
            _detailsStateFlow.update { it.copy(photo = repository.getPhoto(photoId, isFromBookmarks)) }
        }
    }

    fun addToBookmarks() {
        val photo = _detailsStateFlow.value.photo
        viewModelScope.launch {
            repository.addToBookmarks(photo.copy(liked = !photo.liked))
        }
    }


}