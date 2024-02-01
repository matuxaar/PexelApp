package com.example.pexelapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.pexelapp.di.viewmodels.ViewModelKey
import com.example.pexelapp.ui.homescreen.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}