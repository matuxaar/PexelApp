package com.example.pexelapp.ui.navigation

import com.example.pexelapp.R

sealed class BottomNavItem(val screen: Screen, val activeIcon: Int) {
    object Home : BottomNavItem(
        screen = Screen.MainScreen,
        activeIcon = R.drawable.ic_home_icon
    )
    object Bookmark : BottomNavItem(
        screen = Screen.BookmarkScreen,
        activeIcon = R.drawable.ic_bookmark_icon_bottom
    )
}