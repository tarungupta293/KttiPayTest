package com.example.kttipaytest.data.repositories

import com.example.kttipaytest.data.NetworkState
import com.example.kttipaytest.data.retrofit.MovieService

class MovieRepository {

    private var movieService: MovieService = MovieService.getInstance()

    private val api_key = "fc41313308a6fbece76ebf7fd21306c6"

    suspend fun getMoviesList() : NetworkState<Any> {
        val response = movieService.getMoviesList(api_key)
        if (response.isSuccessful) {
            val responseBody = response.body()
            return if (responseBody != null) {
                NetworkState.Success(responseBody.results)
            } else {
                NetworkState.Error(response)
            }

        } else {
            return NetworkState.Error(response)
        }
    }

    suspend fun getMovieDetails(id: String) : NetworkState<Any> {
        val response = movieService.getMovieDetails(id,api_key)
        if (response.isSuccessful) {
            val responseBody = response.body()
            return if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }

        } else {
            return NetworkState.Error(response)
        }
    }
}