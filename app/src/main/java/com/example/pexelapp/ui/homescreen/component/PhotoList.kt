package com.example.pexelapp.ui.homescreen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.ui.homescreen.data.HomeScreenAction

@Composable
fun PhotoList(
    photoList: List<Photo>,
    lazyVerticalStaggeredState: LazyStaggeredGridState,
    onDetailsClickFromHome: (Int) -> Unit
) {
    LazyVerticalStaggeredGrid(
        state = lazyVerticalStaggeredState,
        modifier = Modifier.padding(start = 24.dp, end = 6.dp),
        columns = StaggeredGridCells.Fixed(2),
        content = {
            items(photoList) {
                PhotoItem(
                    url = it.src.original,
                    photoId = it.id,
                    onClick = onDetailsClickFromHome
                )
            }
        }
    )
}
