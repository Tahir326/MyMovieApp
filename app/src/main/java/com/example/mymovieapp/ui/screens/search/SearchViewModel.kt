package com.example.mymovieapp.ui.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovieapp.data.model.Movie
import com.example.mymovieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    var uiState by mutableStateOf(SearchUiState())
        private set

    private var searchJob: Job? = null

    init {
        // Load all movies initially when the screen is first opened
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            val allMovies: List<Movie> = repository.observeMovies().first()
            val filteredMovies = allMovies.filter { movie ->
                movie.releaseDateObj?.before(Date()) ?: false
            }
            uiState = uiState.copy(
                searchResults = filteredMovies,
                isLoading = false
            )
        }
    }

    fun onQueryChanged(newQuery: String) {
        uiState = uiState.copy(query = newQuery)

        // Cancel previous debounce job and start a new search after debounce
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500) // debounce 500ms
            performSearch(newQuery)
        }
    }

    private suspend fun performSearch(query: String) {
        if (query.isBlank()) {
            // If query is empty, reset search results to all movies
            loadMovies()
            return
        }

        uiState = uiState.copy(isLoading = true)  // Show loading when starting search
        val allMovies: List<Movie> = repository.observeMovies().first()
        val filtered = allMovies.filter { movie ->
            val isReleased = movie.releaseDateObj?.before(Date()) ?: false
            isReleased && movie.title.contains(query, ignoreCase = true)
        }
        uiState = uiState.copy(searchResults = filtered, isLoading = false)
    }

    fun clearQuery() {
        uiState = uiState.copy(query = "", searchResults = emptyList(), isLoading = false)
    }
}

