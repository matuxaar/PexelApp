package com.example.pexelapp.data.repositories

import android.app.DownloadManager
import android.os.Environment
import androidx.core.net.toUri
import com.example.pexelapp.domain.DownloadPhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DownloadRepository @Inject constructor(
    private val downloadManager: DownloadManager
) : DownloadPhotoRepository {

    override suspend fun downloadPhotoToDevice(imageUrl: String, photoId: Int): Long =
        withContext(Dispatchers.Default) {
            val request = DownloadManager.Request(imageUrl.toUri())
                .setMimeType("$photoId/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("${photoId}.jpg")
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "${photoId}.jpg"
                )
            downloadManager.enqueue(request)
        }
}