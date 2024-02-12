package com.example.pexelapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_table")
data class PhotoEntity(
    @PrimaryKey
    @ColumnInfo val id: Int,
    @ColumnInfo val width: Int,
    @ColumnInfo val height: Int,
    @ColumnInfo val url: String,
    @ColumnInfo val photographer: String,
    @ColumnInfo val liked: Boolean,
    @Embedded val src: SrcEntity

)