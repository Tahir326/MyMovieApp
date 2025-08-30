package com.example.mymovieapp.data.repository

import com.example.mymovieapp.data.local.MovieDao
import com.example.mymovieapp.data.local.toEntity
import com.example.mymovieapp.data.local.toModel
import com.example.mymovieapp.data.model.Movie
import com.example.mymovieapp.data.remote.MockMovieApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MockMovieApi,
    private val dao: MovieDao,
    private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override suspend fun refreshMovies() = withContext(ioDispatcher) {
        val remote = api.fetchMovies()
        remote.forEach { remoteMovie ->
            val local = dao.getById(remoteMovie.id)
            val merged = remoteMovie.copy(isFavorite = local?.isFavorite ?: remoteMovie.isFavorite)
            dao.upsert(merged.toEntity())
        }
    }

    override fun observeMovies(): Flow<List<Movie>> =
        dao.getAll().map { list ->
            list.map { it.toModel() }.sortedByDescending { it.releaseDateObj }
        }

    override fun observeFavorites(): Flow<List<Movie>> =
        dao.getFavorites().map { list -> list.map { it.toModel() } }

    override suspend fun toggleFavorite(movieId: String) = withContext(ioDispatcher) {
        val entity = dao.getById(movieId) ?: return@withContext
        dao.update(entity.copy(isFavorite = !entity.isFavorite))
    }

    override suspend fun getMovieById(movieId: String): Movie? = withContext(ioDispatcher) {
        dao.getById(movieId)?.toModel()
    }

    // ------------------ Section Flows ------------------
    fun observeUpcoming(): Flow<List<Movie>> =
        observeMovies().map { list ->
            val today = Calendar.getInstance().time
            list.filter { it.releaseDateObj?.after(today) == true }.take(6)
        }

    fun observeCarousel6(): Flow<List<Movie>> =
        observeMovies().map { list ->
            val today = Calendar.getInstance().time
            list.filter { it.releaseDateObj?.after(today) != true }.take(6)
        }

    fun observeTrending(): Flow<List<Movie>> =
        observeMovies().map { list -> list.filter { (it.rating ?: 0.0) >= 7.5 }.take(10) }

    fun observePopular(): Flow<List<Movie>> =
        observeMovies().map { list -> list.sortedByDescending { it.rating ?: 0.0 }.take(12) }
}
