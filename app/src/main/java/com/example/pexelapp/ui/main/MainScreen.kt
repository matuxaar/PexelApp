package com.example.pexelapp.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.ui.bookmarksscreen.BookmarksScreen
import com.example.pexelapp.ui.detailsscreen.DetailsScreen
import com.example.pexelapp.ui.homescreen.HomeScreen
import com.example.pexelapp.ui.navigation.AppNavGraph
import com.example.pexelapp.ui.navigation.BottomNavItem
import com.example.pexelapp.ui.navigation.Screen
import com.example.pexelapp.ui.navigation.rememberNavigationState
import com.example.pexelapp.ui.theme.Red

@Composable
fun MainScreen(viewModelFactoryState: ViewModelFactoryState) {
    BottomNavBar(viewModelFactoryState = viewModelFactoryState)
}


@Composable
private fun BottomNavBar(
    viewModelFactoryState: ViewModelFactoryState
) {
    val navigationState = rememberNavigationState()
    val currentRoute = currentRoute(navController = navigationState.navHostController)
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailsScreen.route) {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colorScheme.background
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
                            modifier = Modifier.background(MaterialTheme.colorScheme.background),
                            selected = selected,
                            onClick = {
                                if (!selected) {
                                    navigationState.navigateTo(item.screen.route)
                                }
                            },
                            icon = {
                                Column(
                                ) {
                                    IconColumn(
                                        icon = item.activeIcon,
                                        selected = selected
                                    )
                                }

                            },
                            selectedContentColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedContentColor = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        snackbarHost = {
            SnackbarHost (
                hostState = snackBarHostState,
                snackbar = {
                    Snackbar(
                        snackbarData = it,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            bookmarkScreenContent = {
                BookmarksScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    onPhotoDetailsClick = {
                        navigationState.navigateToDetails(it, true)
                    },
                    onNavigateToHomeClick = {
                        navigationState.navigateTo(Screen.ROUTE_HOME_SCREEN)
                    }
                )
            },
            homeScreenContent = {
                HomeScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    onDetailsClickFromHome = {
                        navigationState.navigateToDetails(it, false)
                    }
                )
            },
            detailsScreenContent = { photo, isFromBookmarks ->
                DetailsScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    photoId = photo.id,
                    onBackPress = { navigationState.navigateBack() },
                    isFromBookmarks = isFromBookmarks,
                    onNavigateToHomeClick = {
                        navigationState.navigateTo(Screen.ROUTE_HOME_SCREEN)
                    }
                )
            }
        )
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
private fun Selector() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Red)
            .width(24.dp)
            .height(2.dp)
    )
}

@Composable
private fun IconColumn(icon: Int, selected: Boolean) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (selected) {
            Selector()
        }
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}
