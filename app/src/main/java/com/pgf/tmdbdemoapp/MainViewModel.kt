package com.pgf.tmdbdemoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pgf.tmdbdemoapp.dto.TMDB_Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val moviesRepository: TMDB_MoviesRepository) : ViewModel() {

    private val _movies = MutableStateFlow<List<TMDB_Movie>>(emptyList())
    val movies: StateFlow<List<TMDB_Movie>> = _movies

    var currentPage = 1
    private var isLoading = false

    fun getMovies(page: Int = 1) {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            val result = moviesRepository.getMovies(page = page)
            _movies.value = _movies.value + result.results
            currentPage = page
            isLoading = false
        }
    }
}