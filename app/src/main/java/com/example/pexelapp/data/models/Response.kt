package com.example.pexelapp.data.models

import com.example.pexelapp.domain.Photo
import com.squareup.moshi.Json

data class Response(
    @Json(name = "per_page") val perPage: Int = 0,
    @Json(name = "next_page") val nextPage: String = "",
    @Json(name = "total_result") val totalResult: Int = 0,
    val photos: List<Photo> = emptyList(),
    val page: Int = 0
)