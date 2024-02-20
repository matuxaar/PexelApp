package com.example.pexelapp.ui.homescreen.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pexelapp.ui.homescreen.data.HomeScreenState

@Composable
fun FeaturedRow(
    homeScreenState: HomeScreenState,
    onItemSelected: (String) -> Unit
) {
    val featuredCollectionsList = homeScreenState.collections
    var selectedPosition by remember {
        mutableStateOf<Int?>(null)
    }
    LazyRow(
        modifier = Modifier
            .padding(start = 24.dp, top = 24.dp, bottom = 24.dp),
    ) {
        items(featuredCollectionsList.size) { index ->
            val collection = featuredCollectionsList[index]
            FeaturedItem(
                text = collection.title,
                isSelected = selectedPosition == index,
                onItemSelected = {
                    selectedPosition = index
                    onItemSelected(collection.title)
                }
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}