package com.pgf.tmdbdemoapp

import com.pgf.tmdbdemoapp.dto.GetLatestMoviesResponse
import com.pgf.tmdbdemoapp.dto.TMDB_Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TMDB_MoviesRepositoryImpl(
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