package com.example.pexelapp.ui.detailsscreen.data

import com.example.pexelapp.domain.model.Photo

data class DetailsScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val photo: Photo = Photo(),
    val isLiked: Boolean = false
)
