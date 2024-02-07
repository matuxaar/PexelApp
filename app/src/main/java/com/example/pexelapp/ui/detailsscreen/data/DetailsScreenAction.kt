package com.example.pexelapp.ui.detailsscreen.data

sealed class DetailsScreenAction {
    data class Init(val photoId: Int): DetailsScreenAction()
    data object Like: DetailsScreenAction()
    data object BackPress: DetailsScreenAction()
}