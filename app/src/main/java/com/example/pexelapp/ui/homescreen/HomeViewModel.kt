package com.example.pexelapp.ui.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.FeaturedCollection
import com.example.pexelapp.ui.homescreen.data.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    private val _list: MutableStateFlow<List<Photo>> = MutableStateFlow(emptyList())
    val list = _list.asStateFlow()

    private val _searchStateFlow = MutableStateFlow("")
    val searchStateFlow = _searchStateFlow.asStateFlow()

    private val _collectionsStateFlow: MutableStateFlow<List<FeaturedCollection>> =
        MutableStateFlow(emptyList())
    val collectionsStateFlow = _collectionsStateFlow.asStateFlow()

    init {
        _searchStateFlow
            .debounce(300)
            .onEach {
                _list.value = emptyList()
                loadNew(it.isNotEmpty())
            }
            .launchIn(viewModelScope)
    }

    fun setSearch(search: String) {
        _searchStateFlow.update { search }
    }


    fun loadNew(isSearch: Boolean = false) {
        viewModelScope.launch {
            _homeScreenState.value = _homeScreenState.value.copy(isLoading = true)
            if (!isSearch) {
                repository
                    .getCuratedPhotos((_list.value.size / 30))
                    .fold(
                        onSuccess = { newList ->
                            val old = _list.value
                            val new = buildList {
                                addAll(old)
                                addAll(newList)
                            }

                            _list.value = new
                        },
                        onFailure = {
                            if (_list.value.isEmpty()) {
                                _homeScreenState.value =
                                    _homeScreenState.value.copy(isError = true, isLoading = true)
                            }

                        }
                    )
            } else {
                repository
                    .getSearchPhotos((_list.value.size / 30), _searchStateFlow.value)
                    .fold(
                        onSuccess = { newList ->
                            val old = _list.value
                            val new = buildList {
                                addAll(old)
                                addAll(newList)
                            }

                            _list.value = new
                        },
                        onFailure = {
                            if (_list.value.isEmpty()) {
                                _homeScreenState.value =
                                    _homeScreenState.value.copy(isError = true, isLoading = true)
                            }
                        }
                    )
            }
            if (_list.value.isNotEmpty()) {
                _homeScreenState.value = _homeScreenState.value.copy(isLoading = false)
            } else if (_list.value.isEmpty()) {
                _homeScreenState.value =
                    _homeScreenState.value.copy(isLoading = false, isError = true)
            }

        }
    }

    fun getCollections(): List<FeaturedCollection> {
        viewModelScope.launch {
            _collectionsStateFlow.value = repository.getCollections()
        }
        return _collectionsStateFlow.value
    }


}