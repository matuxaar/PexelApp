package com.example.pexelapp.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.domain.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun getCuratedPhotos(): Flow<PagingData<Photo>> {
        return repository.getCuratedPhotos().cachedIn(viewModelScope)
    }


}