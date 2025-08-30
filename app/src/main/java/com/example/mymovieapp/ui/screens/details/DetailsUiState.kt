package com.example.mymovieapp.ui.screens.details

import com.example.mymovieapp.data.model.Movie

data class DetailUiState(
    val movie: Movie? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
