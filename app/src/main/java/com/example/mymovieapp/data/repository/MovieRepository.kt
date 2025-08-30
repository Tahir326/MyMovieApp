package com.example.mymovieapp.data.repository

import com.example.mymovieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    /**
     * Attempts to refresh remote movies and persist them locally.
     * Throws exceptions from the API when network/API errors happen.
     */
    suspend fun refreshMovies()

    /**
     * Flow of all movies (single source of truth: Room)
     */
    fun observeMovies(): Flow<List<Movie>>

    /**
     * Flow of favorite movies
     */
    fun observeFavorites(): Flow<List<Movie>>

    suspend fun toggleFavorite(movieId: String)

    suspend fun getMovieById(movieId: String): Movie?
}
