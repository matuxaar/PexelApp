package com.example.pexelapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pexelapp.domain.model.Photo

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    detailsScreenContent: @Composable (Photo, Boolean) -> Unit,
    bookmarkScreenContent: @Composable () -> Unit,
    homeScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.MainScreen.route
    ) {
        homeScreenNavGraph(
            homeScreenContent = homeScreenContent,
            detailsScreenContent = detailsScreenContent
        )
        composable(Screen.BookmarkScreen.route) {
            bookmarkScreenContent()
        }
    }
}