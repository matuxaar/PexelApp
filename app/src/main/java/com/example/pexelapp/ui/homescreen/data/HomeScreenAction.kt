package com.example.pexelapp.ui.homescreen.data

sealed class HomeScreenAction {
    data object Init: HomeScreenAction()
    data class Search(val query: String): HomeScreenAction()
    data object ErrorHome: HomeScreenAction()
    data object ErrorSearch: HomeScreenAction()
}