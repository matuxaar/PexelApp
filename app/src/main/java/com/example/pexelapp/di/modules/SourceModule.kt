package com.example.pexelapp.di.modules

import android.app.DownloadManager
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides

@Module
class SourceModule {
    @Provides
    fun providesDownloadManager(context: Context): DownloadManager {
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }

    @Provides
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}