package com.example.pexelapp.ui.bookmarksscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelapp.R
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.ui.component.PhotoList
import com.example.pexelapp.ui.theme.White

@Composable
fun BookmarkScreen(viewModelFactoryState: ViewModelFactoryState, onClick: (Photo) -> Unit) {
    val bookmarksViewModel =
        daggerViewModel<BookmarksViewModel>(factory = viewModelFactoryState.viewModelFactory)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar()
        com.example.pexelapp.ui.bookmarksscreen.component.PhotoList(
            photoList = bookmarksViewModel.getLikedPhotos(),
            onClick = onClick
        )
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
            color = White,
            textAlign = TextAlign.Center
        )
    }

}