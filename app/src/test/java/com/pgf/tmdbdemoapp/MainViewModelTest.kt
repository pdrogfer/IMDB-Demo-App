package com.pgf.tmdbdemoapp

import com.pgf.tmdbdemoapp.api.TMDB_MoviesRepository
import com.pgf.tmdbdemoapp.dto.GetMoviesResponse
import com.pgf.tmdbdemoapp.dto.TMDB_Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var repository: TMDB_MoviesRepository
    private lateinit var viewModel: MainViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk<TMDB_MoviesRepository>()
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `getMovies updates movies StateFlow`() = runTest {
        val mockResponse = GetMoviesResponse(
            results = listOf(
                TMDB_Movie(
                    id = 1,
                    title = "Mock Movie",
                    overview = "Overview",
                    release_date = "01012001",
                    poster_path = null
                )
            ),
            page = 1,
            total_pages = 10,
            total_results = 200
        )
        coEvery { repository.getMovies(1) } returns mockResponse

        viewModel.getMovies(1)
        testDispatcher.scheduler.advanceUntilIdle()

        val movies = viewModel.movies.first()
        assert(movies.size == 1)
        assert(movies[0].title == "Mock Movie")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}