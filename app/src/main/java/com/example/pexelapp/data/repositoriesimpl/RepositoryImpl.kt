package com.example.pexelapp.data.repositoriesimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pexelapp.data.mappers.PhotoEntityMapper
import com.example.pexelapp.data.mappers.PhotoMapper
import com.example.pexelapp.data.mappers.PhotoToEntityMapper
import com.example.pexelapp.data.network.PhotoService
import com.example.pexelapp.data.paging.BookmarksPhotoPagingSource
import com.example.pexelapp.data.paging.CuratedPhotoPagingSource
import com.example.pexelapp.data.paging.SearchPagingSource
import com.example.pexelapp.data.source.DataBaseSource
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val photoService: PhotoService,
    private val dataBaseSource: DataBaseSource,
    private val photoMapper: PhotoMapper,
    private val photoToEntityMapper: PhotoToEntityMapper,
    private val photoEntityMapper: PhotoEntityMapper
) : Repository {

    override fun getCuratedPhotos(): Flow<PagingData<Photo>> =
        Pager(config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
            pagingSourceFactory = { CuratedPhotoPagingSource(photoService) }
        ).flow.flowOn(Dispatchers.IO)


    override fun getSearchPhotos(query: String): Flow<PagingData<Photo>> =
        Pager(config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
            pagingSourceFactory = { SearchPagingSource(photoService, query) }
        ).flow.flowOn(Dispatchers.IO)

    override suspend fun getPhoto(id: Int, isFromBookMark: Boolean): Photo = with(Dispatchers.IO) {
        return if (isFromBookMark) {
            photoEntityMapper(dataBaseSource.getPhotoById(id))
        } else {
            photoMapper(photoService.getPhoto(id))
        }
    }



    override suspend fun addToBookmarks(photo: Photo) = with(Dispatchers.IO) {
        dataBaseSource.addToBookmarks(photoToEntityMapper(photo))
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

    override fun likePhoto(photoId: Int) {

    }


    companion object {
        private const val PAGE_SIZE = 30
    }
}