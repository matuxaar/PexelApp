package com.example.pexelapp.ui.detailsscreen.data

import android.content.Context

sealed class DetailsScreenAction {
    data class Init(val photoId: Int): DetailsScreenAction()
    data object Like: DetailsScreenAction()
    data object BackPress: DetailsScreenAction()
    data class Download(
        val imageUrl: String,
        val context: Context,
        val photoId: Int
    ): DetailsScreenAction()
}