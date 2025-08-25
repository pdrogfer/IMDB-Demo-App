package com.pgf.tmdbdemoapp

import com.pgf.tmdbdemoapp.dto.GetLatestMoviesResponse

interface TMDB_MoviesRepository {

    suspend fun getMovies(page: Int): GetLatestMoviesResponse
}