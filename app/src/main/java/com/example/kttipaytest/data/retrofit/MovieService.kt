package com.example.kttipaytest.data.retrofit

import com.example.kttipaytest.domain.dataclass.MovieDetail
import com.example.kttipaytest.domain.dataclass.MovieResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular?language=en-US&page=1")
    suspend fun getMoviesList(@Query("api_key") apiKey: String) : Response<MovieResponse>

    @GET("movie/{movie_id}?language=en-US")
    suspend fun getMovieDetails(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String) : Response<MovieDetail>

    companion object {
        var retrofitService: MovieService? = null
        fun getInstance() : MovieService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(MovieService::class.java)
            }
            return retrofitService!!
        }

    }
}