package com.example.mymovieapp.data.model

import java.text.SimpleDateFormat
import java.util.*

data class Movie(
    val id: String,
    val title: String,
    val releaseDate: String,   // e.g. "dd/MM/yyyy" or "yyyy-MM-dd"
    val rating: Double? = null,
    val description: String? = null, // <-- add this
    val posterUrl: String,
    val isFavorite: Boolean = false
) {
    val releaseDateObj: Date?
        get() {
            val formats = listOf(
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()),
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            )
            for (fmt in formats) {
                try {
                    return fmt.parse(releaseDate)
                } catch (_: Exception) {}
            }
            return null
        }
}
