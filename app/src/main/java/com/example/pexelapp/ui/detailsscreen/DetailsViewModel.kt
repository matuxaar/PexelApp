package com.example.pexelapp.ui.detailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelapp.domain.use_case.AddOrRemoveUseCase
import com.example.pexelapp.domain.use_case.DownloadPhotoUseCase
import com.example.pexelapp.domain.use_case.GetPhotoUseCase
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenAction
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val getPhotoUseCase: GetPhotoUseCase,
    private val addOrRemoveUseCase: AddOrRemoveUseCase,
    private val downloadPhotoUseCase: DownloadPhotoUseCase
) : ViewModel() {

    private val _detailsStateFlow = MutableStateFlow(DetailsScreenState())
    val detailsStateFlow = _detailsStateFlow.asStateFlow()

    fun handleAction(action: DetailsScreenAction, isFromBookmarks: Boolean) {
        when (action) {
            is DetailsScreenAction.Init -> init(action.photoId, isFromBookmarks)
            is DetailsScreenAction.Like -> addOrRemoveToBookmarks(isFromBookmarks)
            is DetailsScreenAction.Download -> savePhotoToDevice(action.imageUrl, action.photoId)
            DetailsScreenAction.BackPress -> backPress()
        }
    }

    private fun init(photoId: Int, isFromBookmarks: Boolean) {
        viewModelScope.launch {
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = true)
            getPhoto(photoId, isFromBookmarks)
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = false)
        }
    }

    private fun backPress() {

    }

    fun getPhoto(photoId: Int, isFromBookmarks: Boolean) {
        viewModelScope.launch {
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = true)
            getPhotoUseCase.execute(photoId, isFromBookmarks).catch {

            }.collect { photo ->
                if (photo != null) {
                    _detailsStateFlow.update { currentState ->
                        currentState.copy(
                            photo = photo,
                            isLiked = isFromBookmarks && photo != null,
                            isLoading = !isFromBookmarks
                        )
                    }
                } else {
                    _detailsStateFlow.update { currentState ->
                        currentState.copy(isError = true)
                    }
                }
            }
            _detailsStateFlow.value = _detailsStateFlow.value.copy(isLoading = false)
        }
    }

    private fun addOrRemoveToBookmarks(isFromBookmarks: Boolean) {
        val photo = _detailsStateFlow.value.photo
        val isLiked = _detailsStateFlow.value.isLiked
        viewModelScope.launch {
            addOrRemoveUseCase.invoke(photo, isFromBookmarks)
            _detailsStateFlow.update { currentState ->
                currentState.copy(isLiked = !isLiked)
            }
        }
    }

    private fun savePhotoToDevice(imageUrl: String, photoId: Int) {
        viewModelScope.launch {
            downloadPhotoUseCase.invoke(imageUrl, photoId)
        }
    }
}