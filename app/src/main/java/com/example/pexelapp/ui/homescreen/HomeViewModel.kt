package com.example.pexelapp.ui.homescreen

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexelapp.domain.Repository
import com.example.pexelapp.domain.model.FeaturedCollection
import com.example.pexelapp.domain.use_case.GetPagingPhotoListUseCase
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
import java.io.IOException
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val getPagingPhotoListUseCase: GetPagingPhotoListUseCase
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    fun handleAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.Init -> init()
            is HomeScreenAction.Search -> setSearch(action.query)
            is HomeScreenAction.ErrorHome -> {
                loadNewPhotos()
                setSearch("")
            }

            is HomeScreenAction.ErrorSearch -> loadNewPhotos(true)
        }
    }

    private fun init() {
        _homeScreenState
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(300)
            .onEach {
                loadNewPhotos(it.isNotEmpty())
            }
            .launchIn(viewModelScope)
        getCollections()
    }

    private fun setSearch(search: String) {
        _homeScreenState.update { currentState ->
            currentState.copy(searchQuery = search)
        }
    }

    private fun loadNewPhotos(isSearch: Boolean = false) {
        viewModelScope.launch {
            _homeScreenState.value = _homeScreenState.value.copy(isLoading = true)
            getPagingPhotoListUseCase.invoke(
                _homeScreenState.value.photoList,
                isSearch,
                _homeScreenState.value.searchQuery
            ).onFailure {
                _homeScreenState.update { currentState ->
                    currentState.copy(
                        isError = true
                    )
                }
                it.printStackTrace()
            }.fold(
                onSuccess = {
                    val newList = buildList {
                        addAll(it)
                    }
                    _homeScreenState.update { currentState ->
                        currentState.copy(
                            photoList = newList,
                            isLoading = false,
                            isError = newList.isEmpty()
                        )
                    }
                },
                onFailure = {
                    if (_homeScreenState.value.photoList.isEmpty()) {
                        _homeScreenState.value =
                            _homeScreenState.value.copy(
                                isError = true,
                                isLoading = true
                            )
                    }
                }
            )
        }
    }

    private fun getCollections(): List<FeaturedCollection> {
        viewModelScope.launch {
            _homeScreenState.update { currentState ->
                currentState.copy(collections = repository.getCollections())
            }
        }
        return _homeScreenState.value.collections
    }
}