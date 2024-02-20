package com.example.pexelapp.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelapp.R
@Composable
fun ExploreButton(onErrorClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .clickable { }
    ) {
        Text(
            text = stringResource(R.string.explore),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.clickable { onErrorClick() }
        )
    }
}

@Composable
fun TryAgainButton(onTryAgainClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .clickable { }
    ) {
        Text(
            text = stringResource(R.string.try_again),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.clickable { onTryAgainClick() }
        )
    }
}