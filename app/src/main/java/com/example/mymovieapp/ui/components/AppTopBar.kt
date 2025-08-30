package com.example.mymovieapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    showSearchIcon: Boolean = false,
    onSearchClicked: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        actions = {
            if (showSearchIcon) {
                IconButton(onClick = onSearchClicked) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        scrollBehavior = null
    )
}
