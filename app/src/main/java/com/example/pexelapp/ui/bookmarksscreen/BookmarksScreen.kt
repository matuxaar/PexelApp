package com.example.pexelapp.ui.bookmarksscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.ui.bookmarksscreen.component.ErrorBookmarks
import com.example.pexelapp.ui.bookmarksscreen.component.PhotoList
import com.example.pexelapp.ui.bookmarksscreen.component.TopBar
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarksScreenAction
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarksScreenState
import com.example.pexelapp.ui.component.HorizontalProgressBar

@Composable
fun BookmarksScreen(
    viewModelFactoryState: ViewModelFactoryState,
    onPhotoDetailsClick: (Int) -> Unit,
    onNavigateToHomeClick: () -> Unit
) {
    val bookmarksViewModel =
        daggerViewModel<BookmarksViewModel>(factory = viewModelFactoryState.viewModelFactory)
    val handleAction: (BookmarksScreenAction) -> Unit = {
        bookmarksViewModel.handleAction(it)
    }
    LaunchedEffect(Unit) {
        handleAction(BookmarksScreenAction.Init)
    }
    val bookmarksScreenState by bookmarksViewModel.bookmarksScreenState.collectAsState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    BookmarksScreenContent(
        onPhotoDetailsClick = onPhotoDetailsClick,
        onNavigateToHomeClick = onNavigateToHomeClick,
        bookmarksScreenState = bookmarksScreenState,
        lazyStaggeredGridState = lazyStaggeredGridState,
    )
}

@Composable
private fun BookmarksScreenContent(
    onPhotoDetailsClick: (Int) -> Unit,
    onNavigateToHomeClick: () -> Unit,
    bookmarksScreenState: BookmarksScreenState,
    lazyStaggeredGridState: LazyStaggeredGridState,
) {
    val isLoading = bookmarksScreenState.isLoading
    val isError = bookmarksScreenState.isError
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar()
        if (isLoading) {
            HorizontalProgressBar()
        }
        if (isError) {
            ErrorBookmarks {
                onNavigateToHomeClick()
            }
        } else {
            PhotoList(
                photoList = bookmarksScreenState.photoList,
                onDetailsClickFromBookmarks = {
                    onPhotoDetailsClick(it)
                },
                onNavigateToHomeClick = {
                    onNavigateToHomeClick()
                },
                lazyStaggeredGridState
            )
        }
    }
}