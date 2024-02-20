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
import kotlinx.coroutines.flow.catch
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
            getPhoto(photoId, isFromBookmarks)
            repository.subscribeToPhoto(photoId)
                .catch {
                    //_detailsStateFlow.value = _detailsStateFlow.value.copy(isError = true)
                }
                .collect { photo ->
                    if (photo != null) {
                        if (isFromBookmarks) {
                            _detailsStateFlow.update { currentState ->
                                currentState.copy(photo = Photo(liked = true))
                            }
                        } else {
                            _detailsStateFlow.update { currentState ->
                                currentState.copy(photo = photo)
                            }
                        }
                    }
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

    fun getPhoto(photoId: Int, isFromBookmarks: Boolean) {
        viewModelScope.launch {
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = true)
            if (isFromBookmarks) {
                repository.subscribeToPhoto(photoId)
                    .catch {
                        //_detailsStateFlow.value = _detailsStateFlow.value.copy(isError = true)
                    }
                    .collect { photo ->
                        if (photo != null) {
                            _detailsStateFlow.update { currentState -> currentState.copy(photo = photo) }
                        }
                    }
            } else {
                repository.getPhoto(photoId)
                    .collect { photo ->
                        if (photo != null) {
                            _detailsStateFlow.update { currentState -> currentState.copy(photo = photo) }
                        }
                    }
            }
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = false)
        }
        _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = false)
    }

    private fun addOrRemoveToBookmarks(isFromBookmarks: Boolean) {
        val photo = _detailsStateFlow.value.photo
        if (photo != null) {
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
    }

    private fun savePhotoToDevice(context: Context, imageUrl: String, photoId: Int) {
        viewModelScope.launch {
            repository.savePhotoToDevice(context, imageUrl, photoId)
        }
    }
}