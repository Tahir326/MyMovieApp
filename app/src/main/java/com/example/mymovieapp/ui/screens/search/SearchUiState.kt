package com.example.mymovieapp.ui.screens.search

import com.example.mymovieapp.data.model.Movie

data class SearchUiState(
    val query: String = "",
    val searchResults: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)
