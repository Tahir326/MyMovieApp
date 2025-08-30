package com.example.mymovieapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymovieapp.data.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: String,
    val title: String,
    val releaseDate: String, // format: "dd/MM/yyyy"
    val rating: Double?,      // nullable
    val description: String?,
    val posterUrl: String,
    val isFavorite: Boolean = false
)

// Map from DB entity → domain model
fun MovieEntity.toModel(): Movie = Movie(
    id = id,
    title = title,
    releaseDate = releaseDate,
    rating = rating,
    description = description,
    posterUrl = posterUrl,
    isFavorite = isFavorite
)

// Map from domain model → DB entity
fun Movie.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    releaseDate = releaseDate,
    rating = rating,
    description = description,
    posterUrl = posterUrl,
    isFavorite = isFavorite
)
