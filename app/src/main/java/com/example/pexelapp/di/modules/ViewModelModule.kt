package com.example.pexelapp.di.modules

import androidx.lifecycle.ViewModel
import com.example.pexelapp.di.viewmodels.ViewModelKey
import com.example.pexelapp.ui.bookmarksscreen.BookmarksViewModel
import com.example.pexelapp.ui.detailsscreen.DetailsViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel::class)
    fun bindBookmarksViewModel(viewModel: BookmarksViewModel): ViewModel
}