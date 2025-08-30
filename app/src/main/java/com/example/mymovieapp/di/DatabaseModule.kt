// File path: app/src/main/java/com/example/mymovieapp/di/DatabaseModule.kt
package com.example.mymovieapp.di

import android.content.Context
import androidx.room.Room
import com.example.mymovieapp.data.local.AppDatabase
import com.example.mymovieapp.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "mymovie_db")
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()
}
