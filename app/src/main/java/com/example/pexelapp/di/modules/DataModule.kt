package com.example.pexelapp.di.modules

import com.example.pexelapp.data.repositories.PhotoRepository
import com.example.pexelapp.domain.Repository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: PhotoRepository): Repository

}