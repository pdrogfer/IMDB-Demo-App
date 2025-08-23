package com.pgf.tmdbdemoapp

import com.pgf.tmdbdemoapp.ui.theme.dto.TMDB_Movie

interface TMDB_MoviesRepository {

    suspend fun getAll(): List<TMDB_Movie>

    suspend fun getById(id: Int): TMDB_Movie?
}