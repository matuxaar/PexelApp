package com.example.pexelapp.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pexelapp.MainActivity
import com.example.pexelapp.data.repositories.DownloadRepository
import com.example.pexelapp.di.modules.DataBaseModule
import com.example.pexelapp.di.modules.DataModule
import com.example.pexelapp.di.modules.NetworkModule
import com.example.pexelapp.di.modules.SourceModule
import com.example.pexelapp.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    ViewModelModule::class,
    DataBaseModule::class,
    DataModule::class,
    SourceModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
}

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    factory: ViewModelProvider.Factory,
    viewModelStoreOwner: ViewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
): VM {
    return viewModel(viewModelStoreOwner, factory = factory)
}

@Immutable
data class ViewModelFactoryState(
    val viewModelFactory: ViewModelProvider.Factory
)