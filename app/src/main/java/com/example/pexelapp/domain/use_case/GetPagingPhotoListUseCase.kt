package com.example.pexelapp.domain.use_case

import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingPhotoListUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun invoke(
        photoList: List<Photo>,
        isSearch: Boolean,
        query: String
    ): Result<List<Photo>> {
        return if (!isSearch) {
            repository.getCuratedPhotos(photoList.size / PAGE_SIZE)
        } else {
            repository.getSearchPhotos(
                page = photoList.size / PAGE_SIZE,
                query = query
            )
        }
    }

    companion object {
        private const val PAGE_SIZE = 30
    }
}