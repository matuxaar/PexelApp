package com.example.pexelapp.ui.detailsscreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pexelapp.R

@Composable
fun AddToBookmarksButton(
    onAddToBookmark: () -> Unit,
    liked: Boolean
) {
    val icon = if (liked) R.drawable.ic_filled_bookmark_icon
    else R.drawable.ic_bookmark_icon
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(60.dp))
            .background(MaterialTheme.colorScheme.onTertiary)
            .size(48.dp)
            .clickable { onAddToBookmark() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = if (liked) MaterialTheme.colorScheme.onPrimary
            else MaterialTheme.colorScheme.onBackground
        )
    }
}