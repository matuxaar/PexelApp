package com.example.pexelapp.ui.navigation

sealed class Screen(
    val route: String
) {
    object HomeScreen: Screen(ROUTE_HOME_SCREEN)
    object DetailsScreen: Screen(ROUTE_DETAILS_SCREEN) {
        private const val ROUTE_FOR_ARGS = "details_screen"

        fun getRouteWithArgs(photoId: Int, isFromBookmarks: Boolean): String {
            return "$ROUTE_FOR_ARGS/$photoId/$isFromBookmarks"
        }
    }
    object MainScreen: Screen(ROUTE_MAIN_SCREEN)

    object BookmarkScreen: Screen(ROUTE_BOOKMARK_SCREEN)

    companion object {
        const val KEY_PHOTO_ID = "photo_id"
        const val KEY_IS_FROM_BOOKMARKS = "false"

        const val ROUTE_MAIN_SCREEN = "main_screen"
        const val ROUTE_HOME_SCREEN = "home_screen"
        const val ROUTE_DETAILS_SCREEN = "details_screen/{$KEY_PHOTO_ID}/{$KEY_IS_FROM_BOOKMARKS}"
        const val ROUTE_BOOKMARK_SCREEN = "bookmark_screen"
    }
}