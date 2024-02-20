package com.example.pexelapp.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.FeaturedCollection
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.ui.homescreen.data.HomeScreenAction
import com.example.pexelapp.ui.homescreen.data.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    fun handleAction(action: HomeScreenAction) {
        when(action) {
            is HomeScreenAction.Init -> init()
        }
    }

    private fun init() {
        _homeScreenState
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(300)
            .onEach {
                _homeScreenState.update { currentState ->
                    currentState.copy(photoList = emptyList())
                }
                loadNew(it.isNotEmpty())
            }
            .launchIn(viewModelScope)
        getCollections()
    }

    fun setSearch(search: String) {
        _homeScreenState.update { currentState ->
            currentState.copy(searchQuery = search)
        }
    }


    fun loadNew(isSearch: Boolean = false) {
        viewModelScope.launch {
            _homeScreenState.value = _homeScreenState.value.copy(isLoading = true)
            if (!isSearch) {
                repository
                    .getCuratedPhotos((_homeScreenState.value.photoList.size / 30))
                    .fold(
                        onSuccess = { newList ->
                            val old = _homeScreenState.value.photoList
                            val new = buildList {
                                addAll(old)
                                addAll(newList)
                            }
                            _homeScreenState.update { currentState ->
                                currentState.copy(photoList = new)
                            }
                        },
                        onFailure = {
                            if (_homeScreenState.value.photoList.isEmpty()) {
                                _homeScreenState.value =
                                    _homeScreenState.value.copy(isError = true, isLoading = true)
                            }
                        }
                    )
            } else {
                repository
                    .getSearchPhotos(
                        (_homeScreenState.value.photoList.size / 30),
                        _homeScreenState.value.searchQuery
                    ).fold(
                        onSuccess = { newList ->
                            val old = _homeScreenState.value.photoList
                            val new = buildList {
                                addAll(old)
                                addAll(newList)
                            }
                            _homeScreenState.update { currentState ->
                                currentState.copy(photoList = new)
                            }
                        },
                        onFailure = {
                            if (_homeScreenState.value.photoList.isEmpty()) {
                                _homeScreenState.value =
                                    _homeScreenState.value.copy(isError = true, isLoading = true)
                            }
                        }
                    )
            }
            if (_homeScreenState.value.photoList.isNotEmpty()) {
                _homeScreenState.value = _homeScreenState.value.copy(isLoading = false)
            } else if (_homeScreenState.value.photoList.isEmpty()) {
                _homeScreenState.value =
                    _homeScreenState.value.copy(isLoading = false, isError = true)
            }
        }
    }

    fun getCollections(): List<FeaturedCollection> {
        viewModelScope.launch {
            _homeScreenState.update { currentState ->
                currentState.copy(collections = repository.getCollections())
            }
        }
        return _homeScreenState.value.collections
    }


}