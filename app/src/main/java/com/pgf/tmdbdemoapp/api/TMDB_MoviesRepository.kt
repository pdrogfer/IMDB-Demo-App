package com.pgf.tmdbdemoapp.api

import com.pgf.tmdbdemoapp.dto.GetMoviesResponse

interface TMDB_MoviesRepository {

    suspend fun getMovies(page: Int): GetMoviesResponse
}