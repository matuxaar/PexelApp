package com.example.pexelapp.data.mappers

import com.example.pexelapp.data.database.PhotoEntity
import com.example.pexelapp.domain.model.Photo
import javax.inject.Inject

class PhotoEntityMapper @Inject constructor() {

    operator fun invoke(photoEntity: PhotoEntity): Photo = with(photoEntity) {
        return Photo(
            id = id,
            width = width,
            height = height,
            url = url,
            photographer = photographer,
            liked = liked,
            src = SrcEntityMapper().invoke(src)
        )
    }
}