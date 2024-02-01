package com.example.pexelapp.data.repositoriesimpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pexelapp.data.network.PhotoService
import com.example.pexelapp.data.paging.CuratedPhotoPagingSource
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val photoService: PhotoService
) : Repository {

    override fun getCuratedPhotos(): Flow<PagingData<Photo>> =
        Pager(config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false
        ),
            pagingSourceFactory = { CuratedPhotoPagingSource(photoService) }
        ).flow.flowOn(Dispatchers.IO)


    override fun getSearchPhotos(query: String): Flow<PagingData<Photo>> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}