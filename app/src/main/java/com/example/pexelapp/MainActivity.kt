package com.example.pexelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.pexelapp.di.ViewModelFactoryState
import com.example.pexelapp.di.viewmodels.ViewModelFactory
import com.example.pexelapp.ui.main.MainScreen
import com.example.pexelapp.ui.theme.PexelAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as DaggerApp).appComponent.inject(this)
        val viewModelFactoryState = ViewModelFactoryState(factory)
        installSplashScreen()
        setContent {
            PexelAppTheme {
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

