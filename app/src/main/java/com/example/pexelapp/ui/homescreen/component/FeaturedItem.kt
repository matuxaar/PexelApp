package com.example.pexelapp.ui.homescreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelapp.R
import com.example.pexelapp.ui.theme.Red
import com.example.pexelapp.ui.theme.White

@Composable
fun FeaturedItem(
    text: String,
    isSelected: Boolean,
    onItemSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(100.dp))
            .height(38.dp)
            .background(
                if (isSelected) Red
                else MaterialTheme.colorScheme.onTertiary
            )
            .clickable { onItemSelected(text) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) White else MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
        )
    }
}