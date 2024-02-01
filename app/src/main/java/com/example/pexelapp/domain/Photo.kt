package com.example.pexelapp.domain

data class Photo(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val liked: Boolean,
    val src: Src
)
