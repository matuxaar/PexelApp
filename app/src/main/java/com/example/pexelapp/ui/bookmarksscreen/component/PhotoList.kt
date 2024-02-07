package com.example.pexelapp.ui.bookmarksscreen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pexelapp.domain.Photo
import kotlinx.coroutines.flow.Flow

@Composable
fun PhotoList(
    photoList: Flow<PagingData<Photo>>,
    onClick: (Photo) -> Unit
) {
    val photoListItems: LazyPagingItems<Photo> = photoList.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier.padding(start = 24.dp, end = 6.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(photoListItems.itemCount) { photo ->
                PhotoItem(
                    url = photoListItems[photo]!!.src.original,
                    photographer = photoListItems[photo]!!.photographer,
                    onClick = onClick,
                    photo = photoListItems[photo]!!
                )
            }
            photoListItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {

                    }

                    loadState.append is LoadState.Loading -> {

                    }

                    loadState.append is LoadState.Error -> {

                    }
                }
            }

        })
}