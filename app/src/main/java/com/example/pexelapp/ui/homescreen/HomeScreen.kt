package com.example.pexelapp.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelapp.R
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.ui.component.ErrorHome
import com.example.pexelapp.ui.component.ErrorSearch
import com.example.pexelapp.ui.component.HorizontalProgressBar
import com.example.pexelapp.ui.component.PhotoList
import com.example.pexelapp.ui.homescreen.data.HomeScreenState
import com.example.pexelapp.ui.theme.Red
import com.example.pexelapp.ui.theme.White

@Composable
fun HomeScreen(
    viewModelFactoryState: ViewModelFactoryState,
    onDetailsClickFromHome: (Int) -> Unit
) {
    val homeViewModel =
        daggerViewModel<HomeViewModel>(factory = viewModelFactoryState.viewModelFactory)

    val photoList by homeViewModel.list.collectAsState()
    val homeScreenState by homeViewModel.homeScreenState.collectAsState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val searchQuery = homeViewModel.searchStateFlow.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeViewModel.loadNew()
    }

    val shouldStartPaginate = remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                lazyStaggeredGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9
            lastVisibleItemIndex >= lazyStaggeredGridState.layoutInfo.totalItemsCount - 6
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value)
            homeViewModel.loadNew()
    }

    HomeScreenContent(
        homeViewModel = homeViewModel,
        photoList = photoList,
        lazyStaggeredGridState = lazyStaggeredGridState,
        onDetailsClickFromHome = onDetailsClickFromHome,
        homeScreenState = homeScreenState,
        onErrorClick = {
            homeViewModel.loadNew()
        },
        onSearchErrorClick = {
            homeViewModel.setSearch(searchQuery.value)
            homeViewModel.loadNew()
        }
    )

}

@Composable
private fun HomeScreenContent(
    homeViewModel: HomeViewModel,
    photoList: List<Photo>,
    lazyStaggeredGridState: LazyStaggeredGridState,
    onDetailsClickFromHome: (Int) -> Unit,
    onErrorClick: () -> Unit,
    onSearchErrorClick: () -> Unit,
    homeScreenState: HomeScreenState
) {
    val searchQuery = homeViewModel.searchStateFlow.collectAsState()
    Column {
        SearchBar(viewModel = homeViewModel)
        if (homeScreenState.isLoading) {
            HorizontalProgressBar()
        }
        if (homeScreenState.isError) {
            if (searchQuery.value.isEmpty()) {
                ErrorSearch(onSearchErrorClick)
            } else if (photoList.isEmpty()) {
                ErrorHome(onErrorClick)
            }
        } else {
            if (searchQuery.value == "") {
                FeaturedRow(
                    homeViewModel,
                    onItemSelected = {
                        homeViewModel.setSearch(it)
                    }
                )

            }
            PhotoList(photoList = photoList, lazyStaggeredGridState) {
                onDetailsClickFromHome(it)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchBar(
    viewModel: HomeViewModel,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchText by viewModel.searchStateFlow.collectAsState()


    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        query = searchText,
        onQueryChange = { text ->
            viewModel.setSearch(text)
        },
        placeholder = {
            Text(
                text = "Search",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                fontWeight = FontWeight(400),
                color = if (searchText.isEmpty()) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onBackground
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search_icon),
                contentDescription = null
            )
        },
        trailingIcon = {
            if (searchText.isNotBlank()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_delete_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        viewModel.setSearch("")
                    }
                )
            }
        },
        onSearch = {
            keyboardController?.hide()
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
        active = false,
        onActiveChange = {
        }
    ) {}
}

@Composable
private fun FeaturedRow(
    viewModel: HomeViewModel,
    onItemSelected: (String) -> Unit
) {
    val featuredCollectionsList = viewModel.getCollections().take(7)
    var selectedPosition by remember {
        mutableStateOf<Int?>(null)
    }
    LazyRow(
        modifier = Modifier
            .padding(start = 24.dp, top = 24.dp, bottom = 24.dp),
    ) {
        items(featuredCollectionsList.size) { index ->
            val collection = featuredCollectionsList[index]
            FeaturedItem(
                text = collection.title,
                isSelected = selectedPosition == index,
                onItemSelected = {
                    selectedPosition = index
                    onItemSelected(collection.title)
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }

}

@Composable
private fun FeaturedItem(
    text: String,
    isSelected: Boolean,
    onItemSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(100.dp))
            .height(38.dp)
            .background(
                if (isSelected) Red
                else MaterialTheme.colorScheme.onTertiary
            )
            .clickable { onItemSelected(text) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            fontWeight = FontWeight(400),
            color = if (isSelected) White else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}



