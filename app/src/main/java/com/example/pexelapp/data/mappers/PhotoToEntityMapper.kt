package com.example.pexelapp.data.mappers

import com.example.pexelapp.data.database.PhotoEntity
import com.example.pexelapp.domain.model.Photo
import javax.inject.Inject

class PhotoToEntityMapper @Inject constructor() {

    operator fun invoke(photo: Photo): PhotoEntity = with(photo) {
        return PhotoEntity(
            id = id,
            width = width,
            height = height,
            url = url,
            photographer = photographer,
            liked = liked,
            src = SrcToEntityMapper().invoke(src)
        )
    }
}