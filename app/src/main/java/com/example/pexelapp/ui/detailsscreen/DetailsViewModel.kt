package com.example.pexelapp.ui.detailsscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenAction
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _detailsStateFlow = MutableStateFlow(DetailsScreenState())
    val detailsStateFlow = _detailsStateFlow.asStateFlow()

    fun handleAction(action: DetailsScreenAction, isFromBookmarks: Boolean) {
        when (action) {
            is DetailsScreenAction.Init -> init(action.photoId, isFromBookmarks)
            is DetailsScreenAction.Like -> addOrRemoveToBookmarks(isFromBookmarks)
            is DetailsScreenAction.Download -> {
                if (!isPhotoDownloaded(action.context, action.photoId)) {
                    savePhotoToDevice(
                        action.context,
                        action.imageUrl,
                        action.photoId
                    )
                }

            }

            DetailsScreenAction.BackPress -> backPress()
        }
    }

    private fun init(photoId: Int, isFromBookmarks: Boolean) {

        viewModelScope.launch {
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = true)
            try {
                val photo = getPhoto(photoId, isFromBookmarks)
                val isLiked = repository.getLikeState(photoId)
                photo.let { photo ->
                    val updatedPhoto = photo.copy(liked = isLiked)
                    _detailsStateFlow.update { currentState -> currentState.copy(photo = updatedPhoto) }
                }
            } catch (e: Exception) {
                _detailsStateFlow.value = _detailsStateFlow.value.copy(isError = true)
            }
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = false)

        }
    }

    private fun isPhotoDownloaded(context: Context, photoId: Int): Boolean {
        var status = false
        viewModelScope.launch {
            status = repository.isPhotoDownloaded(context, photoId)
        }
        return status
    }

    private fun backPress() {

    }

    fun getPhoto(photoId: Int, isFromBookmarks: Boolean): Photo {
        viewModelScope.launch {
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = true)
            if (isFromBookmarks) {
                //if (repository.getLikeState(photoId)) {
                repository.subscribeToPhoto(photoId)
                    .collect { photo ->
                        _detailsStateFlow.update { currentState -> currentState.copy(photo = photo) }
                    }
                //  }
            } else {
                repository.getPhoto(photoId)
                    .collect { photo ->
                        _detailsStateFlow.update { currentState -> currentState.copy(photo = photo) }
                    }
            }

            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = false)
        }
        _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = false)
        return _detailsStateFlow.value.photo
    }

    private fun addOrRemoveToBookmarks(isFromBookmarks: Boolean) {
        val photo = _detailsStateFlow.value.photo
        val newLikedStatus = !photo.liked
        viewModelScope.launch {
            if (isFromBookmarks) {
                repository.removeFromBookmarks(photo.id)
            } else {
                repository.addToBookmarks(photo)
                _detailsStateFlow.update { currentState ->
                    currentState.copy(photo = currentState.photo.copy(liked = newLikedStatus))
                }
            }
            repository.saveLikeState(photo)
            _detailsStateFlow.update { currentState ->
                currentState.copy(photo = currentState.photo.copy(liked = newLikedStatus))
            }
        }

    }

    private fun savePhotoToDevice(context: Context, imageUrl: String, photoId: Int) {
        viewModelScope.launch {
            repository.savePhotoToDevice(context, imageUrl, photoId)
        }
    }
}