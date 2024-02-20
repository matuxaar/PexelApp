package com.example.pexelapp.domain.use_case

import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.Photo
import javax.inject.Inject

class AddOrRemoveUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun invoke(photo: Photo, isFromBookmarks: Boolean) {
        if (isFromBookmarks) {
            repository.removeFromBookmarks(photo.id)
        } else {
            repository.addToBookmarks(photo)
        }
    }
}