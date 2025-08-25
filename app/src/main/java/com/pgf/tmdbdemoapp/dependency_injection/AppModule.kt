package com.pgf.tmdbdemoapp.dependency_injection

import com.pgf.tmdbdemoapp.api.RetrofitClient
import com.pgf.tmdbdemoapp.api.TMDB_MoviesRepositoryImpl
import com.pgf.tmdbdemoapp.api.TmdbApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTmdbApi(): TmdbApiService = RetrofitClient.tmdbApi

    @Provides
    @Singleton
    fun provideRepository(tmdbApiService: TmdbApiService) =
        TMDB_MoviesRepositoryImpl(tmdbApiService)
}