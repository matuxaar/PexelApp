package com.example.pexelapp.ui.bookmarksscreen.data

import androidx.paging.PagingData
import com.example.pexelapp.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class BookmarksScreenState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val photoList: Flow<PagingData<Photo>> = emptyFlow()
)
