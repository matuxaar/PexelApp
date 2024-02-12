package com.example.pexelapp.data.repositoriesimpl

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.pexelapp.data.mappers.OneCollectionMapper
import com.example.pexelapp.data.mappers.PhotoEntityMapper
import com.example.pexelapp.data.mappers.PhotoMapper
import com.example.pexelapp.data.mappers.PhotoToEntityMapper
import com.example.pexelapp.data.network.PhotoService
import com.example.pexelapp.data.paging.BookmarksPhotoPagingSource
import com.example.pexelapp.data.source.DataBaseSource
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.FeaturedCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
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
        }
    }

    override fun getPhotoFromDb(id: Int): Flow<Photo> =
        dataBaseSource.getPhotoById(id).map { photoEntityMapper(it) }


    override suspend fun getCuratedPhotos(page: Int): Result<List<Photo>> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val requestMap = mapOf("page" to page, "per_page" to 30)
                val response = photoService.getCurated(requestMap)
                response.photos
            }
        }

    override suspend fun getSearchPhotos(page: Int, query: String): Result<List<Photo>> =
        withContext(Dispatchers.IO) {
            return@withContext runCatching {
                val response = photoService.search(page = page, 30, query)
                response.photos
            }
        }

    override suspend fun addToBookmarks(photo: Photo) = withContext(Dispatchers.IO) {
        dataBaseSource.addToBookmarks(photoToEntityMapper(photo))
    }

    override suspend fun removeFromBookmarks(photoId: Int) = withContext(Dispatchers.IO) {
        dataBaseSource.removeFromBookmark(photoId)
    }


    override fun getAllImagesFromDb(): Flow<PagingData<Photo>> =
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


    override suspend fun savePhotoToDevice(context: Context, imageUrl: String, photoId: Int) {
        return withContext(Dispatchers.IO) {
            Glide.with(context)
                .asBitmap()
                .load(imageUrl)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        saveBitmapToGallery(context, resource, photoId)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    }

    private fun saveBitmapToGallery(context: Context, bitmap: Bitmap, photoId: Int) {
        val imageUri = MediaStore.Images.Media.insertImage(
            context.contentResolver,
            bitmap,
            "image_$photoId",
            null
        )

        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(imageUri)))
    }

    override suspend fun isPhotoDownloaded(context: Context, photoId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "image_$photoId.jpg"
            )
            file.exists()
        }
    }

    override suspend fun getCollections(): List<FeaturedCollection> {
        val response = photoService.getFeaturedCollections(mapOf())
        return response.collections.map {
            oneCollectionMapper(it)
        }
    }

    override suspend fun getLikeState(photoId: Int): Boolean = withContext(Dispatchers.IO) {
        dataBaseSource.getLikeState(photoId)
    }

    override suspend fun saveLikeState(photo: Photo) = withContext(Dispatchers.IO) {
        dataBaseSource.saveLikeState(photoToEntityMapper(photo))
    }


    companion object {
        private const val PAGE_SIZE = 30
    }
}