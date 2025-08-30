package com.example.mymovieapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.model.Movie
import com.example.mymovieapp.data.repository.MovieRepositoryImpl
import com.example.mymovieapp.domain.usecase.RefreshMoviesUseCase
import com.example.mymovieapp.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepositoryImpl,
    private val refreshMoviesUseCase: RefreshMoviesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadMovies()
    }

    // Inside HomeViewModel.kt
    fun loadMovies() {  // remove 'private'
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                refreshMoviesUseCase()
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message ?: "Failed to load movies") }
            }

            combine(
                repository.observeMovies(),
                repository.observeUpcoming(),
                repository.observeCarousel6(),
                repository.observeTrending(),
                repository.observePopular()
            ) { movies, upcoming, carousel6, trending, popular ->
                HomeUiState(
                    movies = movies,
                    upcoming = upcoming,
                    carousel6 = carousel6,
                    trending = trending,
                    popular = popular,
                    isLoading = false,
                    error = null
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }


    fun toggleFavorite(movieId: String) {
        viewModelScope.launch { toggleFavoriteUseCase(movieId) }
    }
}
