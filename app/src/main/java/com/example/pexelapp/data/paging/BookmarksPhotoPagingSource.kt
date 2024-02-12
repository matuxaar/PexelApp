package com.example.pexelapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pexelapp.data.mappers.PhotoEntityMapper
import com.example.pexelapp.data.source.DataBaseSource
import com.example.pexelapp.domain.model.Photo

class BookmarksPhotoPagingSource(
    private val dataBaseSource: DataBaseSource,
    private val photoEntityMapper: PhotoEntityMapper
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val pageIndex = params.key ?: 1
            val photos =
                dataBaseSource.getAllPhotoFromDb(params.loadSize, pageIndex * params.loadSize)
                    .map { photoEntityMapper(it) }

            return LoadResult.Page(
                data = photos,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (photos.isEmpty()) null else pageIndex + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}