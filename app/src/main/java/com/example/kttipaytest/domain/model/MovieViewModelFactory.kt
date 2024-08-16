package com.example.kttipaytest.domain.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kttipaytest.data.repositories.MovieRepository

class MovieViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            MovieViewModel(MovieRepository()) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}