package com.example.pexelapp.ui.detailsscreen

import android.content.Context
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pexelapp.R
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.ui.component.ErrorDetails
import com.example.pexelapp.ui.component.HorizontalProgressBar
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenAction
import com.example.pexelapp.ui.detailsscreen.data.DetailsScreenState

@Composable
fun DetailsScreen(
    viewModelFactoryState: ViewModelFactoryState,
    photoId: Int,
    onBackPress: () -> Unit,
    isFromBookmarks: Boolean,
    onNavigateToHomeClick: () -> Unit
) {
    val detailsViewModel =
        daggerViewModel<DetailsViewModel>(factory = viewModelFactoryState.viewModelFactory)
    val state = detailsViewModel.detailsStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        detailsViewModel.getPhoto(photoId, isFromBookmarks)
    }

    val handleAction: (DetailsScreenAction) -> Unit = {
        when (it) {
            DetailsScreenAction.BackPress -> onBackPress()
            else -> detailsViewModel.handleAction(it, isFromBookmarks)
        }
    }

    DetailsScreenContent(
        detailsScreenState = state.value,
        detailsActionHandler = handleAction,
        LocalContext.current,
        onNavigateToHomeClick,
    )
}

@Composable
private fun DetailsScreenContent(
    detailsScreenState: DetailsScreenState,
    detailsActionHandler: (DetailsScreenAction) -> Unit,
    context: Context,
    onNavigateToHomeClick: () -> Unit,
) {
    val photo = detailsScreenState.photo
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(photo.photographer,
            onBackPress = {
                detailsActionHandler(DetailsScreenAction.BackPress)
            }
        )
        if (detailsScreenState.isLoading) {
            HorizontalProgressBar()
        } else if (detailsScreenState.isError) {
            ErrorDetails {
                onNavigateToHomeClick()
            }
        }
        PhotoItem(photo = photo)
        BottomRow(
            onClick = {
                detailsActionHandler(DetailsScreenAction.Like)
            },
            detailsScreenState = detailsScreenState,
            onDownloadClick = {
                val downloadAction = DetailsScreenAction.Download(
                    detailsScreenState.photo.src.original,
                    context,
                    detailsScreenState.photo.id
                )
                detailsActionHandler(downloadAction)
            }
        )
    }
}

@Composable
private fun PhotoItem(photo: Photo) {
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Transparent)
            .padding(24.dp)
    ) {
        AsyncImage(
            model = photo.src.original,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_vector_for_placeholder)
        )
    }
}

@Composable
private fun TopBar(photographer: String, onBackPress: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 4.dp),
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
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
}


@Composable
private fun BackButton(onBackPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .size(48.dp)
            .background(MaterialTheme.colorScheme.onTertiary)
            .clickable { onBackPressed() },
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_icon_back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun BottomRow(
    onClick: () -> Unit,
    detailsScreenState: DetailsScreenState,
    onDownloadClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DownloadRow(onDownloadClick)
        AddToBookmarksButton(onClick, detailsScreenState)
    }
}

@Composable
private fun DownloadRow(onDownloadClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(180.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.onTertiary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DownloadButton(onDownloadClick)
        Spacer(modifier = Modifier.size(18.dp))
        Text(
            text = "Download",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp,
            fontWeight = FontWeight(600),
            fontFamily = FontFamily(Font(R.font.mulish_regular))
        )
    }
}

@Composable
private fun DownloadButton(onDownloadClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(60.dp))
            .background(Color.Transparent)
            .clickable { onDownloadClick() },
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
private fun AddToBookmarksButton(
    onAddToBookmark: () -> Unit,
    detailsScreenState: DetailsScreenState
) {
    val icon = if (detailsScreenState.photo.liked) R.drawable.ic_filled_bookmark_icon
    else R.drawable.ic_bookmark_icon
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(60.dp))
            .background(MaterialTheme.colorScheme.onTertiary)
            .size(48.dp)
            .clickable { onAddToBookmark() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if (detailsScreenState.photo.liked) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onBackground
        )
    }
}
