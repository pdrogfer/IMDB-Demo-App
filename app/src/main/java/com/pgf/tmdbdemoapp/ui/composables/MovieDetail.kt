package com.pgf.tmdbdemoapp.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pgf.tmdbdemoapp.BASE_POSTER_PATH
import com.pgf.tmdbdemoapp.R
import com.pgf.tmdbdemoapp.dto.TMDB_Movie
import com.pgf.tmdbdemoapp.navigation.Screen

@Composable
fun MovieDetail(movieNav: Screen.MovieDetailScreen, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        val bannerUrl =
            movieNav.movie.poster_path?.let { "${BASE_POSTER_PATH}${movieNav.movie.poster_path}" }
        val bannerModifier = Modifier
            .fillMaxWidth()
            .height(570.dp)
            .clip(RoundedCornerShape(8.dp))

        AsyncImage(
            model = bannerUrl,
            contentDescription = null,
            modifier = bannerModifier,
            placeholder = painterResource(id = R.drawable.ic_no_image),
            error = painterResource(id = R.drawable.ic_no_image),
            fallback = painterResource(id = R.drawable.ic_no_image)
        )

        Box(
            modifier = modifier,
            contentAlignment = Alignment.TopStart
        ) {
            Column {
                Text(text = "Title: ${movieNav.movie.title}")
                Box(modifier = Modifier.padding(top = 24.dp))
                Text(text = "Overview: ${movieNav.movie.overview}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailPreview() {
    val previewMovie = Screen.MovieDetailScreen(
        movie = TMDB_Movie(
            id = 1,
            title = "Movie 1",
            release_date = "date",
            overview = "overview",
            poster_path = null
        )
    )
    MovieDetail(movieNav = previewMovie)
}