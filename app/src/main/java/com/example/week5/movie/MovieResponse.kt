package com.example.week5.movie

data class MovieResponse (
    val dates: Dates? = null,
    val page: Long? = null,
    val results: List<Movie>? = null,
    val totalPages: Long? = null,
    val totalResults: Long? = null
)
data class TopRatedMovieResponse (
    val page: Long? = null,
    val results: List<Movie>? = null,
    val totalPages: Long? = null,
    val totalResults: Long? = null
)

data class Dates (
    val maximum: String? = null,
    val minimum: String? = null
)