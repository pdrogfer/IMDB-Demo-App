package com.pgf.tmdbdemoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pgf.tmdbdemoapp.ui.theme.dto.TMDB_Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: TMDB_MoviesRepository) : ViewModel() {

    // Using MutableStateFlow to hold the list of persons
    private val _movies = MutableStateFlow<List<TMDB_Movie>>(emptyList())
    val movies: StateFlow<List<TMDB_Movie>> = _movies

    // Using LiveData to hold the person-by-id
    private val _movieById = MutableLiveData<TMDB_Movie?>(null)
    val movieById: LiveData<TMDB_Movie?> = _movieById

    fun getMovies() {
        viewModelScope.launch {
            val movies = moviesRepository.getAll()
            _movies.value = movies
        }
    }

    fun getMovieById(id: Int) {
        viewModelScope.launch {
            val movie = moviesRepository.getById(id)
            _movieById.value = movie
        }
    }
}