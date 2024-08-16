package com.example.kttipaytest.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.kttipaytest.R
import com.example.kttipaytest.domain.dataclass.Movie
import com.example.kttipaytest.domain.dataclass.MovieDetail
import com.example.kttipaytest.domain.model.MovieViewModel
import com.example.kttipaytest.domain.model.MovieViewModelFactory
import com.example.kttipaytest.domain.utils.ConnectionUtil
import com.example.kttipaytest.ui.theme.KttiPayTheme

class MovieDetailActivity : ComponentActivity() {

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MovieViewModelFactory())[MovieViewModel::class.java]

        if (ConnectionUtil.checkInternetConnection(this)) {
            intent.getStringExtra("id")?.let { viewModel.getMovieDetails(it) }
            viewModel.movieDetail.observe(this) {
                updateOnUI(it)
            }
        } else{
            setContent {
                showEmptyUI("No Internet Connection. Please connect with your internet")
            }
        }
    }

    private fun updateOnUI(movieDetail: MovieDetail?) {
        if (movieDetail != null) {
            setContent {
                KttiPayTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MovieDetailScreen(movieDetail)


                    }
                }
            }
        } else {
            setContent {
                showEmptyUI("Details are not available. Please try again later")
            }
        }
    }
}

@Composable
fun MovieDetailScreen(movieDetail: MovieDetail) {
    Column {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movieDetail.backdrop_path}",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = movieDetail.title,
            fontSize = 18.sp,
            color = colorScheme.primary,
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = movieDetail.overview,
            fontSize = 14.sp,
            color = colorScheme.secondary,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start)

        )

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorScheme.primary)) {
                    append("Release date: ")
                }
                withStyle(style = SpanStyle(color = colorScheme.secondary)) {
                    append(movieDetail.release_date)
                } },
            fontSize = 14.sp,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start
                )
        )

        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = colorScheme.primary)) {
                append("Movie duration: ")
                }
                withStyle(style = SpanStyle(color = colorScheme.secondary)) {
                append("${movieDetail.runtime} minutes")
                } },
            fontSize = 14.sp,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start
                )
        )
    }
}

@Composable
private fun showEmptyUI(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(4.dp, 8.dp, 4.dp, 4.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailScreenPreview() {

    KttiPayTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MovieDetailScreen(
                MovieDetail(
                    backdrop_path = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
                    title = "Deadpool & Wolverine",
                    overview = "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, " +
                            "Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again " +
                            "with an even more reluctant Wolverine.",
                    runtime = "128",
                    release_date = "2024-07-24"
                )
            )
        }
    }

}