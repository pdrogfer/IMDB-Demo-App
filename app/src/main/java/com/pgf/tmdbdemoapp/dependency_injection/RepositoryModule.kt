package com.pgf.tmdbdemoapp.dependency_injection

import com.pgf.tmdbdemoapp.api.TMDB_MoviesRepository
import com.pgf.tmdbdemoapp.api.TMDB_MoviesRepositoryImpl
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
    abstract fun bindRepository(
        impl: TMDB_MoviesRepositoryImpl
    ): TMDB_MoviesRepository
}