package com.example.pexelapp.ui.detailsscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pexelapp.R

@Composable
fun DetailsTopBar(photographer: String, onBackPress: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onBackPress)
        Spacer(modifier = Modifier.width(64.dp))
        Photographer(photographer)
    }
}

@Composable
private fun Photographer(photographer: String) {
    Text(
        text = photographer,
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
}


@Composable
private fun BackButton(onBackPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .size(48.dp)
            .background(MaterialTheme.colorScheme.onTertiary)
            .clickable { onBackPressed() },
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_icon_back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}