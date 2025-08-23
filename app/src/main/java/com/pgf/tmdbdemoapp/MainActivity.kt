package com.pgf.tmdbdemoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pgf.tmdbdemoapp.ui.theme.TMDBDemoAppTheme
import com.pgf.tmdbdemoapp.ui.theme.dto.TMDB_Movie
import kotlin.getValue

/*
* 1. make UI for both list and detail views, with fake data from viewmodel -> done
* 2. add new navigation 3 list and detail views
* 3. make view model get data from repository -> done
* add dependency injection with Hilt
* 4. make repository get data from retrofit client -> done
* 5. add viewmodel tests
* 6. add UI tests
* 7. make viewmodel expose state: loading, error, data
*
*/

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        object  : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val repository = TMDB_MoviesRepositoryImpl(
                    RetrofitClient.tmdbApi
                )
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(repository) as T
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBDemoAppTheme {
                val movies by viewModel.movies.collectAsState()
                val selectedMovie by viewModel.movieById.observeAsState()

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(text = "TMDB Movies") },
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column {
                        MovieList(
                            movies = movies,
                            modifier = Modifier
                                .weight(3f)
                                .padding(innerPadding),
                            onMovieClick = { id -> viewModel.getMovieById(id) }
                        )

                        selectedMovie?.let { movie ->
                            // TODO: navigate to a detail view
                            MovieDetail(
                                movie = movie,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp)
                            )
                        } ?: run {
                            Text(
                                text = "Select a movie to see details",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp)
                            )
                        }
                    }
                }
            }
        }

        viewModel.getMovies()
    }
}

@Composable
fun MovieList(movies: List<TMDB_Movie>, modifier: Modifier = Modifier, onMovieClick: (Int) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieItem(movie = movie, onClick = { onMovieClick(movie.id) })
        }
    }
}

@Composable
fun MovieItem(movie: TMDB_Movie, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },

        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Text(
            text = "Title: ${movie.title}\nYear: ${movie.release_date}",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun MovieDetail(movie: TMDB_Movie, modifier: Modifier = Modifier) {
    Text(
        text = "Title: ${movie.title}\nYear: ${movie.release_date}",
        modifier = modifier.padding(16.dp)
    )
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
            onMovieClick = {
                Log.d("MovieListPreview", "Clicked on movie with ID: $it")
            }
        )
    }
}