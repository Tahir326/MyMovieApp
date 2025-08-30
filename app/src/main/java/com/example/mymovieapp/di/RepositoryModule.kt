// File path: app/src/main/java/com/example/mymovieapp/di/RepositoryModule.kt
package com.example.mymovieapp.di

import com.example.mymovieapp.data.repository.MovieRepository
import com.example.mymovieapp.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository
}
