package com.example.pexelapp.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    fun getCuratedPhotos(): Flow<PagingData<Photo>> =
        repository.getCuratedPhotos().cachedIn(viewModelScope)


    fun getSearchPhotos(query: String): Flow<PagingData<Photo>> =
        repository.getSearchPhotos(query).cachedIn(viewModelScope)


}