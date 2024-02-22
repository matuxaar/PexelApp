package com.example.pexelapp.ui.homescreen.data

import com.example.pexelapp.domain.model.FeaturedCollection
import com.example.pexelapp.domain.model.Photo

data class HomeScreenState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val photoList: List<Photo> = emptyList(),
    val collections: List<FeaturedCollection> = emptyList(),
    val searchQuery: String = ""
)
