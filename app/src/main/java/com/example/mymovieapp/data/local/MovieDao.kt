package com.example.mymovieapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.mymovieapp.data.model.Movie
import kotlinx.coroutines.flow.map

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies")
    fun getAll(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): MovieEntity?

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: MovieEntity)

    @Update
    suspend fun update(entity: MovieEntity)

    @Transaction
    suspend fun upsert(entity: MovieEntity) {
        insert(entity)
    }

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
