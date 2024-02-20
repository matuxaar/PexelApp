package com.example.pexelapp.domain.use_case

import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val repository: Repository
) {
    fun execute(photoId: Int, isFromBookmarks: Boolean): Flow<Photo?> {
        return if (isFromBookmarks) {
            repository.subscribeToPhoto(photoId)
        } else {
            repository.getPhoto(photoId)
        }
    }
}