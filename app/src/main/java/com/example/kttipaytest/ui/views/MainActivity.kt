package com.example.kttipaytest.ui.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.kttipaytest.domain.dataclass.Movie
import com.example.kttipaytest.domain.model.MovieViewModel
import com.example.kttipaytest.domain.model.MovieViewModelFactory
import com.example.kttipaytest.domain.utils.ConnectionUtil
import com.example.kttipaytest.ui.theme.KttiPayTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, MovieViewModelFactory())[MovieViewModel::class.java]
        if (ConnectionUtil.checkInternetConnection(this)){
            viewModel.getMoviesList()
            viewModel.movieList.observe(this) {
                updateOnUI(it)
            }
        } else {
            setContent {
                showEmptyUI("No Internet Connection. Please connect with your internet")
            }
        }
    }

    private fun updateOnUI(movieList: List<Movie>) {
        setContent {
            KttiPayTheme {
                // A surface container using the 'background' color from the theme

                if (movieList.isNotEmpty()) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MovieList(movieList) {
                            val intent = Intent(this, MovieDetailActivity::class.java)
                            intent.putExtra("id",it)
                            startActivity(intent)
                        }
                    }
                } else {
                    showEmptyUI("Currently Data is not available. Please try again later")
                }


            }
        }
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

@Composable
private fun MovieList(movieList: List<Movie>, onClick: (id: String)-> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize()
    ) {
        items(movieList.size) {
            MovieData(movieList[it], onClick)
        }
    }
}

@Composable
private fun MovieData(movie: Movie, onClick: (id: String)-> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation =  CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        )
    ) {
        Column(modifier = Modifier
            .clickable {
                onClick(movie.id)
            }) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            Text(
                text = movie.title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(4.dp, 8.dp, 4.dp, 4.dp)
                    .align(CenterHorizontally)
            )

            Text(
                text = "Rating: ${movie.vote_average}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(4.dp, 4.dp, 4.dp, 8.dp)
                    .align(CenterHorizontally))
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun MovieListPreview() {
    val list = ArrayList<Movie>()
    list.add(
        Movie(
            id = "1234",
            poster_path = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
            title = "Deadpool & Wolverine",
            vote_average = "7.837"
        )
    )

    list.add(
        Movie(
            id = "1234",
            poster_path = "/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg",
            title = "Inside Out 2",
            vote_average = "7.837"
        )
    )

    list.add(
        Movie(
            id = "1234",
            poster_path = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
            title = "Deadpool & Wolverine",
            vote_average = "7.837"
        )
    )

    list.add(
        Movie(
            id = "1234",
            poster_path = "/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
            title = "Deadpool & Wolverine",
            vote_average = "7.837"
        )
    )

    KttiPayTheme {
        MovieList(list) {}
    }

}

@Preview(showBackground = true)
@Composable
private fun ShowEmptyUIPreview() {
    showEmptyUI(message = "Something went wrong")
}