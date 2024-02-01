package com.example.pexelapp.ui.navigation

import com.example.pexelapp.R

sealed class BottomNavItem(val route: String, val icon: Int) {
    object Home: BottomNavItem("home", R.drawable.ic_home_icon)
    object Bookmark: BottomNavItem("bookmark", R.drawable.ic_bookmark_icon)
}