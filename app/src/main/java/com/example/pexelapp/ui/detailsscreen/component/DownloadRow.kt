package com.example.pexelapp.ui.detailsscreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pexelapp.R

@Composable
fun DownloadRow(onDownloadClick: () -> Unit) {
    Row(
        modifier = Modifier
            .width(180.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.onTertiary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DownloadButton(onDownloadClick)
        Spacer(modifier = Modifier.size(18.dp))
        Text(
            text = "Download",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelMedium,
        )
    }
}

@Composable
private fun DownloadButton(onDownloadClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(60.dp))
            .background(Color.Transparent)
            .clickable { onDownloadClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_circle_download),
            contentDescription = null
        )
        Image(
            painter = painterResource(id = R.drawable.ic_download_icon),
            contentDescription = null
        )
    }

}