package com.example.pexelapp.data.mappers

import com.example.pexelapp.data.models.OneCollectionResponse
import com.example.pexelapp.domain.model.FeaturedCollection
import javax.inject.Inject

class OneCollectionMapper @Inject constructor() {

    operator fun invoke(response: OneCollectionResponse): FeaturedCollection = with(response) {
        return FeaturedCollection(
            title = title
        )
    }
}