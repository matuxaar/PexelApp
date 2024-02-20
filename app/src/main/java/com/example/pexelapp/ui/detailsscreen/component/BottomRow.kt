package com.example.pexelapp.ui.detailsscreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BottomRow(
    onClick: () -> Unit,
    liked: Boolean,
    onDownloadClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DownloadRow(onDownloadClick)
        AddToBookmarksButton(onClick, liked)
    }
}