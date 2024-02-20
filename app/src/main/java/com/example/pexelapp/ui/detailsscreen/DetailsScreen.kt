package com.example.pexelapp.ui.detailsscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.ui.component.HorizontalProgressBar
import com.example.pexelapp.ui.detailsscreen.component.BottomRow
import com.example.pexelapp.ui.detailsscreen.component.DetailsPhotoItem
import com.example.pexelapp.ui.detailsscreen.component.DetailsTopBar
import com.example.pexelapp.ui.detailsscreen.component.ErrorDetails
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
        onNavigateToHomeClick,
    )
}

@Composable
private fun DetailsScreenContent(
    detailsScreenState: DetailsScreenState,
    detailsActionHandler: (DetailsScreenAction) -> Unit,
    onNavigateToHomeClick: () -> Unit,
) {
    val photo = detailsScreenState.photo
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        DetailsTopBar(
            photo.photographer,
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
        DetailsPhotoItem(photo = photo)
        BottomRow(
            onClick = {
                detailsActionHandler(DetailsScreenAction.Like)
            },
            liked = detailsScreenState.isLiked,
            onDownloadClick = {
                val downloadAction = DetailsScreenAction.Download(
                    detailsScreenState.photo.src.original
                )
                detailsActionHandler(downloadAction)
            }
        )
    }
}