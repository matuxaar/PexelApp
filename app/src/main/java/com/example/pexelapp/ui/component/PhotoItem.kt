package com.example.pexelapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pexelapp.domain.Photo

@Composable
fun PhotoItem(url: String, photo: Photo, photoId: Int, onClick: (Photo) -> Unit) {
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .width(155.dp)
            .padding(top = 12.dp, end = 18.dp)
            .clickable { onClick(photo) }
    ) {
        AsyncImage(model = url, contentDescription = null)
    }
}

