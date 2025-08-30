package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.data.model.Movie
import com.example.mymovieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<List<Movie>> = repository.observeMovies()
}
