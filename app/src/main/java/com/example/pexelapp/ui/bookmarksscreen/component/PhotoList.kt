package com.example.pexelapp.ui.bookmarksscreen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pexelapp.domain.model.Photo
import kotlinx.coroutines.flow.Flow

@Composable
fun PhotoList(
    photoList: Flow<PagingData<Photo>>,
    onDetailsClickFromBookmarks: (Int) -> Unit,
    onNavigateToHomeClick: () -> Unit,
    lazyVerticalStaggeredState: LazyStaggeredGridState
) {
    val photoListItems: LazyPagingItems<Photo> = photoList.collectAsLazyPagingItems()

    LazyVerticalStaggeredGrid(
        state = lazyVerticalStaggeredState,
        modifier = Modifier.padding(start = 24.dp, end = 6.dp, top = 40.dp),
        columns = StaggeredGridCells.Fixed(2),
        content = {
            items(photoListItems.itemCount) { index ->
                val photo = photoListItems[index] ?: return@items
                PhotoItem(
                    url = photo.src.original,
                    photographer = photo.photographer,
                    onClick = onDetailsClickFromBookmarks,
                    photoId = photo.id
                )
            }
//            photoListItems.apply {
//                when{
//                    loadState.refresh is LoadState.Loading -> {
//                        item {
//                            HorizontalProgressBar()
//                        }
//                    }
//                    loadState.refresh is LoadState.Error -> {
//                        item {
//                            ErrorBookmarks {
//                                onNavigateToHomeClick()
//                            }
//                        }
//                    }
//                    loadState.append is LoadState.Loading -> {
//                        item {
//                            HorizontalProgressBar()
//                        }
//                    }
//                    loadState.append is LoadState.Error -> {
//                        item {
//                            ErrorBookmarks {
//                                onNavigateToHomeClick()
//                            }
//                        }
//                    }
//                }
//            }
        }
    )
}


