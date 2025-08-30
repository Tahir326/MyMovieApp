package com.example.mymovieapp.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onHomeClicked: () -> Unit,
    onFavoritesClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onSettingsClicked: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    NavigationBar(
        containerColor = if (isDark) Color(0xFF333333) else MaterialTheme.colorScheme.surface
    ) {
        // 1. Home
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = onHomeClicked
        )

        // 2. Favorites
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = currentRoute == "favorites",
            onClick = onFavoritesClicked
        )

        // 3. Search
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = currentRoute == "search",
            onClick = onSearchClicked
        )

        // 4. Settings
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentRoute == "settings",
            onClick = onSettingsClicked
        )
    }
}
