package com.example.pexelapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pexelapp.data.mappers.OneCollectionMapper
import com.example.pexelapp.data.mappers.PhotoEntityMapper
import com.example.pexelapp.data.mappers.PhotoMapper
import com.example.pexelapp.data.mappers.PhotoToEntityMapper
import com.example.pexelapp.data.network.PhotoService
import com.example.pexelapp.data.paging.BookmarksPhotoPagingSource
import com.example.pexelapp.data.source.DataBaseSource
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.FeaturedCollection
import com.example.pexelapp.domain.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photoService: PhotoService,
    private val dataBaseSource: DataBaseSource,
    private val photoMapper: PhotoMapper,
    private val photoToEntityMapper: PhotoToEntityMapper,
    private val photoEntityMapper: PhotoEntityMapper,
    private val oneCollectionMapper: OneCollectionMapper
) : Repository {

    override fun getPhoto(id: Int): Flow<Photo> {
        return flow {
            emit(photoMapper(photoService.getPhoto(id)))
        }.flowOn(Dispatchers.IO)
    }

    override fun subscribeToPhoto(id: Int): Flow<Photo> =
        dataBaseSource.getPhotoById(id)
            .map { photoEntity ->
                photoEntityMapper(photoEntity)
            }

    override suspend fun getCuratedPhotos(page: Int): Result<List<Photo>> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val requestMap = mapOf("page" to page, "per_page" to PAGE_SIZE)
                val response = photoService.getCurated(requestMap)
                response.photos
            }
        }

    override suspend fun getSearchPhotos(page: Int, query: String): Result<List<Photo>> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val response = photoService.search(page = page, PAGE_SIZE, query)
                response.photos
            }
        }

    override suspend fun addToBookmarks(photo: Photo) = withContext(Dispatchers.IO) {
        dataBaseSource.addToBookmarks(photoToEntityMapper(photo))
    }

    override suspend fun removeFromBookmarks(photoId: Int) = withContext(Dispatchers.IO) {
        dataBaseSource.removeFromBookmark(photoId)
    }

    override fun subscribeToPhotos(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                BookmarksPhotoPagingSource(
                    dataBaseSource, photoEntityMapper
                )
            },
            initialKey = 0
        ).flow.flowOn(Dispatchers.IO)

//    override suspend fun savePhotoToDevice(imageUrl: String): Long {
//        withContext(Dispatchers.Default) {
//            try {
//                val request = DownloadManager.Request(imageUrl.toUri())
//                    .setMimeType("image/jpeg")
//                    //.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
//                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//                    .setTitle("image.jpg")
//                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpg")
//            } catch (e: Exception) {
//
//            }
//        }
//    }

//    override suspend fun isPhotoDownloaded(context: Context, photoId: Int): Boolean {
//        return withContext(Dispatchers.IO) {
//            val file = File(
//                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
//                "image_$photoId.jpg"
//            )
//            file.exists()
//        }
//    }

    override suspend fun getCollections(): List<FeaturedCollection> {
        val response = photoService.getFeaturedCollections(mapOf())
        return response.collections.map {
            oneCollectionMapper(it)
        }
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}