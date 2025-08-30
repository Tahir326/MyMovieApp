package com.example.mymovieapp.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState(isLoading = true))
    val uiState: StateFlow<DetailUiState> = _uiState

    fun loadMovie(movieId: String) {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            _uiState.value = DetailUiState(isLoading = true)

            try {
                val movie = repository.getMovieById(movieId)

                // Minimum loading time (400ms)
                val elapsed = System.currentTimeMillis() - startTime
                val minLoadingTime = 400L
                if (elapsed < minLoadingTime) {
                    kotlinx.coroutines.delay(minLoadingTime - elapsed)
                }

                if (movie != null) {
                    _uiState.value = DetailUiState(movie = movie, isLoading = false)
                } else {
                    _uiState.value = DetailUiState(error = "Movie not found", isLoading = false)
                }
            } catch (e: Exception) {
                _uiState.value = DetailUiState(error = e.localizedMessage ?: "Unknown error", isLoading = false)
            }
        }
    }

    fun toggleFavorite() {
        val currentMovie = _uiState.value.movie ?: return
        viewModelScope.launch {
            repository.toggleFavorite(currentMovie.id)
            val updatedMovie = currentMovie.copy(isFavorite = !currentMovie.isFavorite)
            _uiState.value = _uiState.value.copy(movie = updatedMovie)
        }
    }
}
