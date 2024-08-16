package com.example.kttipaytest.domain.dataclass

data class MovieResponse(

    val results: List<Movie>,
    val page: Int,
    val total_pages: Int
    )

data class Movie(
    val id: String,
    val poster_path: String,
    val title: String,
    val vote_average: String
)