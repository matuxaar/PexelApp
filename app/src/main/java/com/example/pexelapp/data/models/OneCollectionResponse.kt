package com.example.pexelapp.data.models

import com.squareup.moshi.Json

data class OneCollectionResponse(
    @Json(name = "title") val title: String
)
