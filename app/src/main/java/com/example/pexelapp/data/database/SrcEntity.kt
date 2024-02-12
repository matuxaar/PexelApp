package com.example.pexelapp.data.database

import androidx.room.ColumnInfo

data class SrcEntity(
    @ColumnInfo(name = "landscape") val landscape: String,
    @ColumnInfo(name = "large") val large: String,
    @ColumnInfo(name = "large2x") val large2x: String,
    @ColumnInfo(name = "medium") val medium: String,
    @ColumnInfo(name = "original") val original: String,
    @ColumnInfo(name = "portrait") val portrait: String,
    @ColumnInfo(name = "small") val small: String,
    @ColumnInfo(name = "tiny") val tiny: String
)
