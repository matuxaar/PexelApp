package com.example.pexelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.daggerViewModel
import com.example.pexelapp.di.viewmodels.ViewModelFactory
import com.example.pexelapp.ui.detailsscreen.DetailsScreen
import com.example.pexelapp.ui.detailsscreen.DetailsViewModel
import com.example.pexelapp.ui.homescreen.HomeScreen
import com.example.pexelapp.ui.homescreen.HomeViewModel
import com.example.pexelapp.ui.main.MainScreen
import com.example.pexelapp.ui.navigation.AppNavGraph
import com.example.pexelapp.ui.navigation.Screen
import com.example.pexelapp.ui.navigation.rememberNavigationState
import com.example.pexelapp.ui.theme.PexelAppTheme
import dagger.internal.DaggerGenerated
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as DaggerApp).appComponent.inject(this)
        val viewModelFactoryState = ViewModelFactoryState(factory)
        setContent {
            PexelAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PexelAppTheme {
                        MainScreen(viewModelFactoryState = viewModelFactoryState)
                    }
                }
            }
        }
    }

}

