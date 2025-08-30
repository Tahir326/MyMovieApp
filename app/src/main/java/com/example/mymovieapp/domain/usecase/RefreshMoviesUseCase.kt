package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.data.repository.MovieRepository
import javax.inject.Inject

class RefreshMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke() {
        repository.refreshMovies()
    }
}
