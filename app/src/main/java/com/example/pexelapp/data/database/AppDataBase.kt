package com.example.pexelapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhotoEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun getPhotoDao(): PhotoDao
}