package com.example.pexelapp.ui.bookmarksscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pexelapp.R
import com.example.pexelapp.domain.Photo
import com.example.pexelapp.ui.theme.White

@Composable
fun PhotoItem(url: String, photo: Photo, photographer: String, onClick: (Photo) -> Unit) {
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .width(155.dp)
            .padding(top = 12.dp, end = 18.dp)
            .clickable { onClick(photo) }
    ) {
        Column {
            AsyncImage(model = url, contentDescription = null)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0x66000000)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = photographer,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                fontWeight = FontWeight(400),
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}