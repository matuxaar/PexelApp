package com.example.pexelapp.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.pexelapp.R
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.ui.component.PhotoList
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(viewModelFactoryState: ViewModelFactoryState, onClick: (Photo) -> Unit) {
    val homeViewModel =
        daggerViewModel<HomeViewModel>(factory = viewModelFactoryState.viewModelFactory)
    Column {
        SearchBar(viewModel = homeViewModel) {
            onClick(it)
        }
        Spacer(modifier = Modifier.size(24.dp))
        PhotoList(photoList = homeViewModel.getCuratedPhotos()) {
            onClick(it)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar(
    viewModel: HomeViewModel,
    onClick: (Photo) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchText = remember {
        mutableStateOf("")
    }
    val isActive = remember {
        mutableStateOf(false)
    }
    val isTextNotEmpty = searchText.value.isNotBlank()
    var photoList: Flow<PagingData<Photo>> = if (searchText.value.isBlank()) {
        viewModel.getCuratedPhotos()
    } else {
        viewModel.getSearchPhotos(searchText.value)
    }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        query = searchText.value,
        onQueryChange = { text ->
            searchText.value = text
        },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search_icon),
                contentDescription = null
            )
        },
        trailingIcon = {
            if (isTextNotEmpty) {
                Image(
                    painter = painterResource(id = R.drawable.ic_delete_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        searchText.value = ""
                    }
                )
            }
        },
        onSearch = { text ->
            isActive.value = false
            if (searchText.value.isNotBlank()) {
                photoList = viewModel.getSearchPhotos(searchText.value)
            }
            keyboardController?.hide()
        },
//        colors = SearchBarDefaults.colors(
//            containerColor = TODO(),
//            inputFieldColors = TODO()
//        ),
        active = isActive.value,
        onActiveChange = {
            isActive.value = it
        }
    ) {
        PhotoList(photoList = photoList, onClick = {
            onClick(it)
        })
    }
}

@Composable
private fun FeaturedRow() {

}


