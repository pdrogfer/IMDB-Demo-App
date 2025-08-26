package com.pgf.tmdbdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pgf.tmdbdemoapp.navigation.Screen
import com.pgf.tmdbdemoapp.ui.composables.MovieDetail
import com.pgf.tmdbdemoapp.ui.theme.TMDBDemoAppTheme
import com.pgf.tmdbdemoapp.ui.composables.MovieList
import com.pgf.tmdbdemoapp.ui.composables.SearchDialog
import dagger.hilt.android.AndroidEntryPoint
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
* - add search functionality -> done
* - add pagination -> done
*
*/

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val backStack = rememberNavBackStack<Screen>(Screen.MovieListScreen)
            var showSearch by remember { mutableStateOf(false) }

            TMDBDemoAppTheme {
                val movies by viewModel.movies.collectAsState()
                val isListScreen = backStack.toList().lastOrNull() is Screen.MovieListScreen

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(text = "TMDB Movies") },
                            modifier = Modifier.shadow(8.dp)
                        )
                    },
                    floatingActionButton = {
                        if (isListScreen) {
                            FloatingActionButton(
                                onClick = { showSearch = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavDisplay(
                        backStack = backStack,
                        entryProvider = { key ->
                            when (key) {
                                is Screen.MovieListScreen -> NavEntry(key = key) {
                                    if (showSearch) {
                                        SearchDialog(
                                            movies = movies,
                                            onDismiss = { showSearch = false },
                                            onMovieClick = { movie ->
                                                val route = Screen.MovieDetailScreen(movie)
                                                backStack.add(route)
                                            }
                                        )
                                    }
                                    MovieList(
                                        movies = movies,
                                        modifier = Modifier
                                            .padding(8.dp),
                                        getNextPage = {
                                            viewModel.getMovies(viewModel.currentPage + 1)
                                        },
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

