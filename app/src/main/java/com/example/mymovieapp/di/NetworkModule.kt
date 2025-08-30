package com.example.mymovieapp.di

import com.example.mymovieapp.data.remote.MockMovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMockMovieApi(): MockMovieApi = MockMovieApi()
}
