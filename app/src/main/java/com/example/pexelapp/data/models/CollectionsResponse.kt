package com.example.pexelapp.data.models

import com.squareup.moshi.Json

data class CollectionsResponse(
    @Json(name = "collections") val collections: List<OneCollectionResponse>
)