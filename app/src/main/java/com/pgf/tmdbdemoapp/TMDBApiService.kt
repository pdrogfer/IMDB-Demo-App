package com.pgf.tmdbdemoapp

import com.pgf.tmdbdemoapp.ui.theme.dto.GetLatestMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {
    @GET("discover/movie")
    suspend fun getLatestMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "release_date.desc",
        @Query("page") page: Int = 1
    ): GetLatestMoviesResponse
}