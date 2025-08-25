package com.pgf.tmdbdemoapp.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pgf.tmdbdemoapp.dto.TMDB_Movie
import com.pgf.tmdbdemoapp.navigation.Screen
import com.pgf.tmdbdemoapp.ui.theme.TMDBDemoAppTheme

@Composable
fun MovieList(movies: List<TMDB_Movie>,
              modifier: Modifier = Modifier,
              navigateToDetail: (entry: Screen.MovieDetailScreen) -> Unit = {}) {
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieItem(movie = movie, onClick = {
                val entry = Screen.MovieDetailScreen(movie)
                navigateToDetail(entry)
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PersonListPreview() {
    TMDBDemoAppTheme {
        val previewMovies = List(20) {
            TMDB_Movie(
                id = it,
                title = "Movie $it",
                release_date = "date",
                overview = "overview",
                poster_path = "poster path"
            )
        }
        MovieList(
            movies = previewMovies,
            modifier = Modifier.padding(8.dp),
            navigateToDetail = { Log.d("MovieListPreview", "Clicked on movie: ${it.movie.title}") }
        )
    }
}
