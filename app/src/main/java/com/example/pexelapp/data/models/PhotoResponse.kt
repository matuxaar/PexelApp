package com.example.pexelapp.data.models

import com.squareup.moshi.Json

data class PhotoResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "width") val width: Int? = null,
    @Json(name = "height") val height: Int? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "photographer") val photographer: String? = null,
    @Json(name = "liked") val liked: Boolean? = null
)