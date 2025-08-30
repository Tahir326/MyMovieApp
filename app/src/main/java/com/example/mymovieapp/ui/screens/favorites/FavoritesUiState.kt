package com.example.mymovieapp.ui.screens.favorites

import com.example.mymovieapp.data.model.Movie

data class FavoritesUiState(
    val isLoading: Boolean = false,
    val favorites: List<Movie> = emptyList(),
    val error: String? = null
)
