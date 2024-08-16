package com.example.kttipaytest.domain.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kttipaytest.data.NetworkState
import com.example.kttipaytest.data.repositories.MovieRepository
import com.example.kttipaytest.domain.dataclass.Movie
import com.example.kttipaytest.domain.dataclass.MovieDetail
import kotlinx.coroutines.launch

class MovieViewModel constructor(private val movieRepository: MovieRepository): ViewModel() {

    val movieList = MutableLiveData<List<Movie>>()

    val movieDetail = MutableLiveData<MovieDetail>()

    fun getMoviesList() {
        viewModelScope.launch {
            when (val response = movieRepository.getMoviesList()) {
                is NetworkState.Success -> {
                    movieList.postValue(response.data as List<Movie>)
                }

                is NetworkState.Error -> {
                    movieList.postValue(ArrayList())
                }
            }
        }
    }

    fun getMovieDetails(id: String) {
        viewModelScope.launch {
            when (val response = movieRepository.getMovieDetails(id)) {
                is NetworkState.Success -> {
                    movieDetail.postValue(response.data as MovieDetail)
                }

                is NetworkState.Error -> {
                    movieDetail.postValue(null)
                }
            }
        }
    }
}