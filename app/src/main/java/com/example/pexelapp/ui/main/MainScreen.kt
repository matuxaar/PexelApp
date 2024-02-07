package com.example.pexelapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.ui.bookmarksscreen.BookmarkScreen
import com.example.pexelapp.ui.detailsscreen.DetailsScreen
import com.example.pexelapp.ui.homescreen.HomeScreen
import com.example.pexelapp.ui.navigation.AppNavGraph
import com.example.pexelapp.ui.navigation.BottomNavItem
import com.example.pexelapp.ui.navigation.rememberNavigationState
import com.example.pexelapp.ui.theme.DarkGray
import com.example.pexelapp.ui.theme.Red

@Composable
fun MainScreen(viewModelFactoryState: ViewModelFactoryState) {
    BottomNavBar(viewModelFactoryState = viewModelFactoryState)
}


@Composable
private fun BottomNavBar(viewModelFactoryState: ViewModelFactoryState) {
    val navigationState = rememberNavigationState()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.background
            ) {
                val navBackStackEntry by navigationState
                    .navHostController
                    .currentBackStackEntryAsState()

                val items = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Bookmark
                )
                items.forEach { item ->
                    val selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.route == item.screen.route
                    } ?: false

                    BottomNavigationItem(
                        modifier = Modifier.background(MaterialTheme.colors.background),
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navigationState.navigateTo(item.screen.route)
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = null
                            )
                        },
                        selectedContentColor = Red,//MaterialTheme.colors.onPrimary,
                        unselectedContentColor = DarkGray
                    )
                }
            }
        }
    ) { paddingValues ->
        val isFromBookmark = false
        AppNavGraph(
            navHostController = navigationState.navHostController,
            bookmarkScreenContent = { BookmarkScreen(
                viewModelFactoryState =  viewModelFactoryState,
                onClick = {
                    navigationState.navigateToDetails(it.id)
                }
            ) },
            homeScreenContent = {
                HomeScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    onClick = {
                        navigationState.navigateToDetails(it.id)
                    }
                )
            },
            detailsScreenContent = {
                DetailsScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    photoId = it.id,
                    onClick = {it},
                    onBackPress = { navigationState.navigateBack() },
                    isFromBookmarks = false
                )
            }
        )
    }

}