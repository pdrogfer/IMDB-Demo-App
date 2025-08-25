package com.pgf.tmdbdemoapp.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pgf.tmdbdemoapp.dto.TMDB_Movie

@Composable
fun SearchDialog(
    movies: List<TMDB_Movie>,
    onDismiss: () -> Unit,
    onMovieClick: (TMDB_Movie) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val filteredMovies = movies.filter { it.title.contains(query, ignoreCase = true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Search Movies") },
        modifier = Modifier.height(400.dp),
        text = {
            Column {
                TextField(
                    value = query,
                    onValueChange = { query = it },
                    label = { Text("Search") }
                )
                Spacer(Modifier.height(16.dp))
                LazyColumn {
                    items(filteredMovies) { movie ->
                        Text(movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    onMovieClick(movie)
                                    onDismiss()
                                }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Close") }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun SearchDialogPreview() {
    val sampleMovies = List(10) {
        TMDB_Movie(
            id = it,
            title = "Movie $it",
            release_date = "2023-01-0$it",
            overview = "Overview of movie $it",
            poster_path = null
        )
    }
    SearchDialog(
        movies = sampleMovies,
        onDismiss = {},
        onMovieClick = {}
    )
}