package com.example.pexelapp.ui.detailsscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.pexelapp.R
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenAction
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenState
import com.example.pexelapp.ui.theme.Black
import com.example.pexelapp.ui.theme.Gray

@Composable
fun DetailsScreen(
    viewModelFactoryState: ViewModelFactoryState,
    photoId: Int,
    onClick: () -> Photo,
    onBackPress: () -> Unit,
    isFromBookmarks: Boolean
) {
    val detailsViewModel =
        daggerViewModel<DetailsViewModel>(factory = viewModelFactoryState.viewModelFactory)
    val state by detailsViewModel.detailsStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        detailsViewModel.getPhoto(photoId, isFromBookmarks)
    }

    val handleAction: (DetailsScreenAction) -> Unit = {
        when (it) {
            DetailsScreenAction.BackPress -> onBackPress()
            else -> detailsViewModel.handleAction(it, isFromBookmarks)
        }
    }

    DetailsScreenContent(detailsScreenState = state, detailsActionHandler = handleAction)


}

@Composable
private fun DetailsScreenContent(
    detailsScreenState: DetailsScreenState,
    detailsActionHandler: (DetailsScreenAction) -> Unit,
) {
    val photo = detailsScreenState.photo
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//        AnimatedVisibility(visible = state.isLoading) {
//
//        }
//        if (state.isLoading) {
//            CircularProgressIndicator()
//        }
        TopBar(photo.photographer,
            onBackPress = {
                detailsActionHandler(DetailsScreenAction.BackPress)
            }
        )
        PhotoItem(photo = photo)
        BottomRow(
            onClick = {
                detailsActionHandler(DetailsScreenAction.Like)
            },
            liked = photo.liked
        )
    }
}

@Composable
private fun PhotoItem(photo: Photo) {
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Transparent)
            .padding(horizontal = 24.dp, vertical = 21.dp)
    ) {
        AsyncImage(model = photo.src.original, contentDescription = null)
    }
}

@Composable
private fun TopBar(photographer: String, onBackPress: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onBackPress)
        Spacer(modifier = Modifier.width(64.dp))
        Photographer(photographer)
    }
}

@Composable
private fun Photographer(photographer: String) {
    Text(
        text = photographer,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.mulish_regular)),
        fontWeight = FontWeight(700),
        textAlign = TextAlign.Center
    )
}


@Composable
private fun BackButton(onBackPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .size(48.dp)
            .background(Gray)
            .clickable { onBackPressed() },
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_icon_back),
            contentDescription = null
        )
    }
}

@Composable
private fun BottomRow(onClick: () -> Unit, liked: Boolean) {
    Row(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DownloadRow()
        AddToBookmarksButton(onClick, liked)
    }
}

@Composable
private fun DownloadRow() {
    Row(
        modifier = Modifier
            .width(180.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Gray),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        DownloadButton()
        Spacer(modifier = Modifier.size(18.dp))
        Text(
            text = "Download",
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontSize = 14.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.mulish_regular))
        )
    }
}

@Composable
private fun DownloadButton(imageUrl: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { }
            .background(Color.Transparent)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_circle_download),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.ic_download_icon),
            contentDescription = null
        )
    }

}

@Composable
private fun AddToBookmarksButton(onClick: () -> Unit, liked: Boolean) {
    val icon = if (liked) R.drawable.ic_filled_bookmark_icon else R.drawable.ic_bookmark_icon
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(60.dp))
            .background(Gray)
            .size(48.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            alignment = Alignment.Center,
        )

    }
}

//fun saveImageToDevice(imageUrl: String) {
//    val request = ImageRequest.Builder()
//        .data(imageUrl)
//        .build()
//
//    val file = Coil.imageLoader().execute(request)
//}