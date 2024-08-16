package com.example.kttipaytest.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {

    var apiService: MovieService? = null
    fun getInstance() : MovieService {
        if (apiService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://howtodoandroid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(MovieService::class.java)
        }
        return apiService!!
    }

}