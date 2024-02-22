package com.example.pexelapp.ui.detailsscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pexelapp.R
import com.example.pexelapp.domain.model.Photo

@Composable
fun DetailsPhotoItem(photo: Photo) {
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Transparent)
            .padding(24.dp)
    ) {
        AsyncImage(
            model = photo.src.original,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_vector_for_placeholder)
        )
    }
}