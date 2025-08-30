package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.data.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(movieId: String) = repository.toggleFavorite(movieId)
}
