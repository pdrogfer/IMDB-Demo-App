package com.pgf.tmdbdemoapp.ui.theme.dto

data class GetLatestMoviesResponse(
    val page: Int,
    val results: List<TMDB_Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class TMDB_Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val poster_path: String?
)