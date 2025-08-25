package com.pgf.tmdbdemoapp.api

import com.pgf.tmdbdemoapp.dto.GetLatestMoviesResponse
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TMDB_MoviesRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : TMDB_MoviesRepository {

    private val apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NjAyOTFiN2Q2ZWY0OTU5NGRjOThlNzZjYTQxZmIyZCIsIm5iZiI6MTQ0NjQ2NjQ4OC4zNTYsInN1YiI6IjU2Mzc1M2I4YzNhMzY4MWI0YjAxYjEzMCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Hk32S4chmVX6ZctmzTSKc3qL9Hw1tT57RkdDHfM4N-E"

    override suspend fun getMovies(page: Int): GetLatestMoviesResponse {
        return withContext(Dispatchers.IO) {
            apiService.getMovies(
                apiKey = apiKey,
                page = page
            )
        }
    }
}