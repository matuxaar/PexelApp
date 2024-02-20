package com.example.pexelapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pexelapp.R

@Preview
@Composable
fun PlaceholderItem() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .width(155.dp)
            .height(170.dp)
            .padding(top = 12.dp, end = 18.dp)
            .background(MaterialTheme.colorScheme.onTertiary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_vector_for_placeholder),
            contentDescription = null
        )
    }
}