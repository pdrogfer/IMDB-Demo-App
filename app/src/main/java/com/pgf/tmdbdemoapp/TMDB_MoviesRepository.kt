package com.pgf.tmdbdemoapp

import com.pgf.tmdbdemoapp.dto.TMDB_Movie

interface TMDB_MoviesRepository {

    suspend fun getAll(): List<TMDB_Movie>

    suspend fun getById(id: Int): TMDB_Movie?
}