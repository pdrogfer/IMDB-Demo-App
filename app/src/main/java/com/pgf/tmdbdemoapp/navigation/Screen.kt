package com.pgf.tmdbdemoapp.navigation

import androidx.navigation3.runtime.NavKey
import com.pgf.tmdbdemoapp.dto.TMDB_Movie
import kotlinx.serialization.Serializable

sealed interface Screen : NavKey {

    @Serializable
    data object MovieListScreen : Screen

    @Serializable
    data class MovieDetailScreen(val movie: TMDB_Movie) : Screen
}