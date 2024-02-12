package com.example.pexelapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.pexelapp.domain.model.Photo

fun NavGraphBuilder.homeScreenNavGraph(
    homeScreenContent: @Composable () -> Unit,
    detailsScreenContent: @Composable (Photo, Boolean) -> Unit
) {
    navigation(
        startDestination = Screen.HomeScreen.route,
        route = Screen.MainScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            homeScreenContent()
        }
        composable(Screen.DetailsScreen.route) {
            val photoId = it.arguments?.getString(Screen.KEY_PHOTO_ID)?.toInt() ?: 0
            val isFromBookmarks = it.arguments?.getString(Screen.KEY_IS_FROM_BOOKMARKS).toBoolean()
            detailsScreenContent(Photo(id = photoId), isFromBookmarks)
        }
    }

}