package com.pgf.tmdbdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pgf.tmdbdemoapp.navigation.Screen
import com.pgf.tmdbdemoapp.ui.composables.MovieDetail
import com.pgf.tmdbdemoapp.ui.theme.TMDBDemoAppTheme
import com.pgf.tmdbdemoapp.ui.composables.MovieList
import kotlin.getValue

/*
* 1. make UI for both list and detail views, with fake data from viewmodel -> done
* 2. add new Navigation 3 for list and detail views -> done
* 3. make view model get data from repository -> done
* - show images with Coil -> done
* 4. add dependency injection with Hilt
* 5. make repository get data from retrofit client -> done
* 6. add viewmodel tests
* 7. add UI tests
* 8. make viewmodel expose state: loading, error, data
*
* - add search functionality
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

