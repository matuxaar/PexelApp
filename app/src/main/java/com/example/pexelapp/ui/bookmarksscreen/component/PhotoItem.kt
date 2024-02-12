package com.example.pexelapp.ui.bookmarksscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.pexelapp.R
import com.example.pexelapp.ui.theme.PexelAppTheme
import com.example.pexelapp.ui.theme.White

@Composable
fun PhotoItem(url: String, photoId: Int, photographer: String, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .width(155.dp)
            .padding(top = 12.dp, end = 18.dp)
            .clickable { onClick(photoId) }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            AsyncImage(
                model = url,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.ic_vector_for_placeholder)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color(0x66000000))
                    .alpha(0.4f)
                    .size(width = 155.dp, height = 33.dp),
            )
            Text(
                text = photographer,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.mulish_regular)),
                fontWeight = FontWeight(400),
                color = White,
                modifier = Modifier.padding(top = 6.dp, bottom = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    PexelAppTheme {
        Surface {
            PhotoItem(
                url = "",
                photoId = 1,
                photographer = "Blablabla",
                onClick = {}
            )
        }
    }
}