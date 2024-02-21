package com.example.pexelapp.domain.use_case

import com.example.pexelapp.domain.DownloadPhotoRepository
import javax.inject.Inject

class DownloadPhotoUseCase @Inject constructor(
    private val downloadPhotoRepository: DownloadPhotoRepository
) {
    suspend fun invoke(imageUrl: String, photoId: Int) {
        downloadPhotoRepository.downloadPhotoToDevice(imageUrl, photoId)
    }
}