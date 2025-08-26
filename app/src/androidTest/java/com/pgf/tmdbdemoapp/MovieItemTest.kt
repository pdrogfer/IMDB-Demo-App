package com.pgf.tmdbdemoapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.pgf.tmdbdemoapp.dto.TMDB_Movie
import com.pgf.tmdbdemoapp.ui.composables.MovieItem
import org.junit.Rule
import org.junit.Test

class MovieItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun movieItem_displaysTitleAndYear() {
        val movie = TMDB_Movie(
            id = 1,
            title = "Test Movie",
            release_date = "2024",
            overview = "Test overview",
            poster_path = null
        )

        composeTestRule.setContent {
            MovieItem(movie = movie, onClick = {})
        }

        composeTestRule.onNodeWithText("Title: Test Movie\nYear: 2024").assertExists()
    }
}