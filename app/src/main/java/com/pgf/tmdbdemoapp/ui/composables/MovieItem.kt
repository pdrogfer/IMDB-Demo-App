package com.pgf.tmdbdemoapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pgf.tmdbdemoapp.BASE_POSTER_PATH
import com.pgf.tmdbdemoapp.R
import com.pgf.tmdbdemoapp.dto.TMDB_Movie

@Composable
fun MovieItem(movie: TMDB_Movie, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },

        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.Companion.padding(16.dp)) {
            val thumbnailUrl = movie.poster_path?.let { "${BASE_POSTER_PATH}${movie.poster_path}" }
            val thumbnailModifier = Modifier.Companion
                .padding(end = 16.dp)
                .width(60.dp)
                .height(90.dp)
                .clip(RoundedCornerShape(8.dp))

            AsyncImage(
                model = thumbnailUrl,
                contentDescription = null,
                modifier = thumbnailModifier,
                placeholder = painterResource(id = R.drawable.ic_no_image),
                error = painterResource(id = R.drawable.ic_no_image),
                fallback = painterResource(id = R.drawable.ic_no_image)

            )

            Text(
                text = "Title: ${movie.title}\nYear: ${movie.release_date}",
                modifier = Modifier.Companion
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    val previewMovie = TMDB_Movie(
        id = 1,
        title = "Movie 1",
        release_date = "date",
        overview = "overview",
        poster_path = null
    )
    MovieItem(movie = previewMovie, onClick = {})
}