package com.example.mymovieapp.ui.screens.home

import com.example.mymovieapp.data.model.Movie

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val carousel6: List<Movie> = emptyList(),
    val trending: List<Movie> = emptyList(),
    val upcoming: List<Movie> = emptyList(),
    val popular: List<Movie> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = true
)
