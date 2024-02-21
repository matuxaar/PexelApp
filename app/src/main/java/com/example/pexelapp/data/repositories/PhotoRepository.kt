package com.example.pexelapp.data.repositories

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
    private val oneCollectionMapper: OneCollectionMapper,
    private val connectivityManager: ConnectivityManager
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
            }.flowOn(Dispatchers.IO)

    override suspend fun getCuratedPhotos(page: Int): Result<List<Photo>> =
        withContext(Dispatchers.IO) {
            return@withContext if (isInternetAvailable()) {
                runCatching {
                    val requestMap = mapOf("page" to page, "per_page" to PAGE_SIZE)
                    val response = photoService.getCurated(requestMap)
                    response.photos
                }
            } else {
                val cachedPhotos = dataBaseSource.getAllPhotos().map { photoEntityMapper(it) }
                if (cachedPhotos.isNotEmpty()) {
                    Result.success(cachedPhotos)
                } else {
                    Result.failure(Exception("No internet connection"))
                }
            }
        }

    override suspend fun getSearchPhotos(page: Int, query: String): Result<List<Photo>> =
        withContext(Dispatchers.IO) {
            return@withContext if (isInternetAvailable()) {
                runCatching {
                    val response = photoService.search(page = page, PAGE_SIZE, query)
                    response.photos
                }
            } else {
                val cachedPhotos = dataBaseSource.getAllPhotos().map { photoEntityMapper(it) }
                if (cachedPhotos.isNotEmpty()) {
                    Result.success(cachedPhotos)
                } else {
                    Result.failure(Exception("No internet connection"))
                }
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

    override suspend fun getCollections(): List<FeaturedCollection> = withContext(Dispatchers.IO) {
        val response = photoService.getFeaturedCollections(mapOf())
        response.collections.map {
            oneCollectionMapper(it)
        }
    }

    private suspend fun isInternetAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            val network = connectivityManager.activeNetwork ?: return@withContext false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return@withContext false
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}