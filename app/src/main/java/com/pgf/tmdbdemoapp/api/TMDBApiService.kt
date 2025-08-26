package com.pgf.tmdbdemoapp.api

import com.pgf.tmdbdemoapp.dto.GetMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "release_date.desc",
        @Query("page") page: Int
    ): GetMoviesResponse
}