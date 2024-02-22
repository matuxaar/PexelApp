package com.example.pexelapp.ui.homescreen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pexelapp.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PexelSearchBar(
    setSearch: (String) -> Unit,
    searchText: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        query = searchText,
        onQueryChange = { text ->
            setSearch(text)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                style = MaterialTheme.typography.labelSmall,
                color = if (searchText.isEmpty()) MaterialTheme.colorScheme.onSurface
                else MaterialTheme.colorScheme.onBackground
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search_icon),
                contentDescription = null
            )
        },
        trailingIcon = {
            if (searchText.isNotBlank()) {
                Image(
                    painter = painterResource(id = R.drawable.ic_delete_icon),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        setSearch("")
                    }
                )
            }
        },
        onSearch = {
            keyboardController?.hide()
        },
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
        active = false,
        onActiveChange = {
        }
    ) {}
}