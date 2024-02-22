package com.example.pexelapp.ui.homescreen.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pexelapp.R

@Composable
fun PhotoItem(url: String, photoId: Int, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .padding(top = 12.dp, end = 18.dp)
            .clickable { onClick(photoId) }
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.ic_vector_for_placeholder)
        )
    }
}

