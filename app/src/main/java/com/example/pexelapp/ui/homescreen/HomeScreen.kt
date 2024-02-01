package com.example.pexelapp.ui.homescreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.pexelapp.domain.Photo
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    PhotoList(photoList = viewModel.getCuratedPhotos())
}


@Composable
private fun BottomNavBar() {
    val navController = rememberNavController()
//    Scaffold(
//        bottomBar = {
//            BottomNavigation {
//                val navBackStackEntry by navController.currentBackStackEntryAsState()
//                val currentDestination = navBackStackEntry?.destination
//                items
//            }
//        }
//    )
}


@Composable
private fun PhotoItem(photo: Photo) {
    Box(
        modifier = Modifier
            .width(155.dp)
            .clickable { TODO() }
    ) {
        AsyncImage(model = photo.src.portrait, contentDescription = null)
    }
}

@Composable
private fun PhotoList(photoList: Flow<PagingData<Photo>>) {
    val photoListItems: LazyPagingItems<Photo> = photoList.collectAsLazyPagingItems()

    LazyVerticalGrid(
        modifier = Modifier.padding(24.dp),
        columns = GridCells.Fixed(2),
        content = {
           items(photoListItems.itemCount) {photo ->
               PhotoItem(photo = photoListItems[photo]!!)
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

@Composable
private fun SearchBar() {

}


