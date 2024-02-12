package com.example.pexelapp.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.pexelapp.data.database.AppDataBase
import com.example.pexelapp.data.database.PhotoDao
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): AppDataBase {

        return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
            .build()

    }

    @Provides
    @Singleton
    fun providePhotoDao(db: AppDataBase): PhotoDao = db.getPhotoDao()

    companion object {
        private const val DATABASE_NAME = "database_name"
    }
}