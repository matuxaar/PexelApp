package com.example.pexelapp.ui.homescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.ui.component.HorizontalProgressBar
import com.example.pexelapp.ui.homescreen.component.ErrorHome
import com.example.pexelapp.ui.homescreen.component.ErrorSearch
import com.example.pexelapp.ui.homescreen.component.FeaturedRow
import com.example.pexelapp.ui.homescreen.component.PexelSearchBar
import com.example.pexelapp.ui.homescreen.component.PhotoList
import com.example.pexelapp.ui.homescreen.data.HomeScreenAction
import com.example.pexelapp.ui.homescreen.data.HomeScreenState

@Composable
fun HomeScreen(
    viewModelFactoryState: ViewModelFactoryState,
    onDetailsClickFromHome: (Int) -> Unit
) {
    val homeViewModel =
        daggerViewModel<HomeViewModel>(factory = viewModelFactoryState.viewModelFactory)
    val homeScreenState by homeViewModel.homeScreenState.collectAsState()
    val handleAction: (HomeScreenAction) -> Unit = {
        homeViewModel.handleAction(it)
    }

    LaunchedEffect(Unit) {
        handleAction(HomeScreenAction.Init)
    }

    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    val shouldStartPaginate = remember {
        derivedStateOf {
            val lastVisibleItemIndex =
                lazyStaggeredGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -9
            lastVisibleItemIndex >= lazyStaggeredGridState.layoutInfo.totalItemsCount - 6
        }
    }

    LaunchedEffect(key1 = shouldStartPaginate.value) {
        if (shouldStartPaginate.value) {
            handleAction(HomeScreenAction.Init)
        }
    }

    HomeScreenContent(
        onDetailsClickFromHome = onDetailsClickFromHome,
        homeScreenState = homeScreenState,
        homeActionHandler = handleAction,
        lazyStaggeredGridState = lazyStaggeredGridState
    )
}

@Composable
private fun HomeScreenContent(
    homeActionHandler: (HomeScreenAction) -> Unit,
    lazyStaggeredGridState: LazyStaggeredGridState,
    onDetailsClickFromHome: (Int) -> Unit,
    homeScreenState: HomeScreenState
) {
    val searchQuery = homeScreenState.searchQuery
    Column {
        PexelSearchBar(
            setSearch = {
                homeActionHandler(HomeScreenAction.Search(it))
            },
            searchText = searchQuery
        )
        if (homeScreenState.isLoading) {
            HorizontalProgressBar()
        }
        if (homeScreenState.isError) {
            if (searchQuery.isEmpty()) {
                ErrorSearch{
                    homeActionHandler(HomeScreenAction.ErrorSearch)
                }
            } else {
                ErrorHome{
                    homeActionHandler(HomeScreenAction.ErrorHome)
                }
            }
        } else {
            AnimatedVisibility(searchQuery.isEmpty()) {
                FeaturedRow(
                    homeScreenState,
                    onItemSelected = {
                        homeActionHandler(HomeScreenAction.Search(it))
                    }
                )
            }
            PhotoList(
                photoList = homeScreenState.photoList,
                lazyVerticalStaggeredState = lazyStaggeredGridState,
                onDetailsClickFromHome =  {
                    onDetailsClickFromHome(it)
                }
            )
        }
    }
}