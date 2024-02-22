package com.example.pexelapp.di.modules

import android.app.DownloadManager
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class SourceModule {
    @Provides
    fun providesDownloadManager(context: Context): DownloadManager {
        return context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }
}