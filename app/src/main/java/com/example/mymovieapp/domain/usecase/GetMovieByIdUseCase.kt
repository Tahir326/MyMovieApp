package com.example.mymovieapp.domain.usecase

import com.example.mymovieapp.data.model.Movie
import com.example.mymovieapp.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieByIdUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(id: String): Movie? = repository.getMovieById(id)
}
