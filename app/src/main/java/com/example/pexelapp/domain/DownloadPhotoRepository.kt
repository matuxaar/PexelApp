package com.example.pexelapp.domain

interface DownloadPhotoRepository {

    suspend fun downloadPhotoToDevice(imageUrl: String, photoId: Int): Long
}