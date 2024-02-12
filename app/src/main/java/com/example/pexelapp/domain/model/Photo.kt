package com.example.pexelapp.domain.model

data class Photo(
    val id: Int = 0,
    val width: Int = 0,
    val height: Int = 0,
    val url: String = "",
    val photographer: String = "",
    val liked: Boolean = false,
    val src: Src = Src()
)
