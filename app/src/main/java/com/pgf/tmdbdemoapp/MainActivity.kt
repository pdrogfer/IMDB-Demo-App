package com.pgf.tmdbdemoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pgf.tmdbdemoapp.navigation.Screen
import com.pgf.tmdbdemoapp.ui.theme.TMDBDemoAppTheme
import com.pgf.tmdbdemoapp.ui.theme.dto.TMDB_Movie
import kotlin.getValue

/*
* 1. make UI for both list and detail views, with fake data from viewmodel -> done
* 2. add new Navigation 3 for list and detail views -> done
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
            val backStack = rememberNavBackStack<Screen>(Screen.MovieListScreen)

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
                    NavDisplay(
                        backStack = backStack,
                        entryProvider = { key ->
                            when (key) {
                                is Screen.MovieListScreen -> NavEntry(key = key) {
                                    MovieList(
                                        movies = movies,
                                        modifier = Modifier
                                            .padding(innerPadding),
                                        navigateToDetail = { route ->
                                            backStack.add(route)
                                        }
                                    )
                                }
                                is Screen.MovieDetailScreen -> NavEntry(key = key) {
                                    MovieDetail(
                                        movieNav = key,
                                        modifier = Modifier
                                            .padding(innerPadding)
                                    )
                                }
                                else -> throw IllegalArgumentException("Unknown key: $key")
                            }
                        }
                    )
                }
            }
        }

        viewModel.getMovies()
    }
}

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
fun MovieDetail(movieNav: Screen.MovieDetailScreen, modifier: Modifier = Modifier) {
    Text(
        text = "Title: ${movieNav.movie.title}\nYear: ${movieNav.movie.release_date}",
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
            navigateToDetail = { Log.d("MovieListPreview", "Clicked on movie: ${it.movie.title}") }
        )
    }
}