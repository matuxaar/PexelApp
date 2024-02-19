package com.example.pexelapp.ui.bookmarksscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import com.example.pexelapp.R
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.ui.bookmarksscreen.component.PhotoList
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarksScreenAction
import com.example.pexelapp.ui.bookmarksscreen.data.BookmarksScreenState
import com.example.pexelapp.ui.component.ErrorBookmarks
import com.example.pexelapp.ui.component.HorizontalProgressBar
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenAction
import kotlinx.coroutines.flow.Flow

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

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(top = 24.dp),
            text = "Bookmarks",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            fontWeight = FontWeight(700),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }

}