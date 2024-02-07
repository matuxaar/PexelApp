package com.example.pexelapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pexelapp.data.network.PhotoService
import com.example.pexelapp.domain.Photo

class SearchPagingSource(
    private val photoService: PhotoService,
    private val query: String
): PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val pageIndex = params.key ?: 1
            val response = photoService.search(pageIndex, params.loadSize, query)

            LoadResult.Page(
                data = response.photos,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (response.photos.isEmpty()) null else pageIndex + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}