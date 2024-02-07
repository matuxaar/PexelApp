package com.example.pexelapp.ui.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pexelapp.domain.Photo
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PhotoList(
    photoList: Flow<PagingData<Photo>>,
    onClick: (Photo) -> Unit
) {
    val photoListItems: LazyPagingItems<Photo> = photoList.collectAsLazyPagingItems()

    FlowRow(
        modifier = Modifier.padding(start = 24.dp, end = 6.dp),
    ) {
        photoList { photo ->
            PhotoItem(
                url = photoListItems[photo]!!.src.original,
                photoId = photoListItems[photo]!!.id,
                photo = photoListItems[photo]!!,
                onClick = onClick
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

    }
}