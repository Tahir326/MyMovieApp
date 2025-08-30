package com.example.mymovieapp.ui.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.mymovieapp.data.model.Movie
import androidx.compose.foundation.border

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    onOpenDetails: (String) -> Unit
) {
    val state = viewModel.uiState
    val gridState = rememberLazyGridState()

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize() // Column fills max size
                    .padding(paddingValues)
            ) {
                // Search Bar
                TextField(
                    value = state.query,
                    onValueChange = { viewModel.onQueryChanged(it) },
                    placeholder = { Text("Search movies by title") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface) // Background instead of border
                        .border(1.dp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp)) // Dynamic border color
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (state.searchResults.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No movies found")
                    }
                } else {
                    // Box to center the LazyVerticalGrid inside
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // Set width to fill, not size
                            .wrapContentHeight(), // Ensure grid height wraps around content
                        contentAlignment = Alignment.TopCenter // Align grid at top
                    ) {
                        LazyVerticalGrid(
                            state = gridState,
                            columns = GridCells.Fixed(2), // 2 columns grid
                            modifier = Modifier
                                .fillMaxWidth(0.9f) // Grid takes 90% of the width of the screen
                                .wrapContentHeight(), // Ensure grid height wraps around content
                            horizontalArrangement = Arrangement.spacedBy(28.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            items(state.searchResults, key = { it.id }) { movie ->
                                MovieGridItem(movie) { onOpenDetails(movie.id) }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MovieGridItem(
    movie: Movie,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(150.dp) // Define width for grid item
            .clickable(onClick = onClick)
    ) {
        // Apply image fade from bottom to top
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            androidx.compose.ui.graphics.Color.Transparent,
                            androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.7f)
                        ),
                        startY = 0f,
                        endY = 300f
                    )
                ),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        val rating = movie.rating ?: 0.0
        val fullStars = (rating / 2).toInt() // Assuming 10-point rating
        val emptyStars = 5 - fullStars

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Display full stars
            repeat(fullStars) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = androidx.compose.ui.graphics.Color(0xFFFF8C00),
                    modifier = Modifier.size(14.dp)
                )
            }

            // Display empty stars
            repeat(emptyStars) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                    modifier = Modifier.size(14.dp)
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = String.format("%.1f", rating),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
