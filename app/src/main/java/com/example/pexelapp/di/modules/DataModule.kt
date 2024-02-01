package com.example.pexelapp.di.modules

import com.example.pexelapp.data.repositoriesimpl.RepositoryImpl
import com.example.pexelapp.domain.Repository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl: RepositoryImpl): Repository

}