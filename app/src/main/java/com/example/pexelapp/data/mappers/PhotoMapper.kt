package com.example.pexelapp.data.mappers

import com.example.pexelapp.data.models.PhotoResponse
import com.example.pexelapp.domain.model.Photo
import com.example.pexelapp.domain.model.Src
import javax.inject.Inject

class PhotoMapper @Inject constructor() {

    operator fun invoke(photoResponse: PhotoResponse): Photo = with(photoResponse) {
        return Photo(
            id = id ?: 0,
            width = width ?: 0,
            height = height ?: 0,
            url = url.orEmpty(),
            photographer = photographer.orEmpty(),
            src = src ?: Src()

            )
    }
}