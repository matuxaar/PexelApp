package com.example.pexelapp.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelapp.R

@Composable
fun ErrorSearch(onTryAgainClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_wifi_error_icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(125.dp, 100.dp)
        )
        TryAgainButton(onTryAgainClick)
    }
}

@Composable
fun ErrorBookmarks(onNavigateToHomeClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "You haven't saved anything yet",
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            color = MaterialTheme.colorScheme.onBackground
        )
        ExploreButton(onNavigateToHomeClick)
    }
}

@Composable
fun ErrorHome(onCuratedPhotoClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No results found",
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            color = MaterialTheme.colorScheme.onBackground
        )
        ExploreButton(
            onCuratedPhotoClick
        )
    }
}

@Composable
fun ErrorDetails(onNavigateToHomeClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Image not found",
            fontSize = 14.sp,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            color = MaterialTheme.colorScheme.onBackground
        )
        ExploreButton(onNavigateToHomeClick)
    }
}

@Composable
private fun ExploreButton(onErrorClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .clickable { }
    ) {
        Text(
            text = "Try Again",
            fontSize = 18.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.clickable { onErrorClick() }
        )
    }
}

@Composable
private fun TryAgainButton(onTryAgainClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .clickable { }
    ) {
        Text(
            text = "Try Again",
            fontSize = 18.sp,
            fontWeight = FontWeight(700),
            fontFamily = FontFamily(Font(R.font.mulish_regular)),
            modifier = Modifier.clickable { onTryAgainClick() }
        )
    }
}