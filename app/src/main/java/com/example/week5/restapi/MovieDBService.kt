package com.example.week5.restapi

import com.example.week5.movie.MovieResponse
import com.example.week5.movie.TopRatedMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieDBService {
    @GET("movie/now_playing")
    suspend fun listNowPlayMovies(
        @Query("language") language: String, @Query("page") page: Int,
    ) : MovieResponse

    @GET("movie/top_rated")
    suspend fun listTopRatedMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): TopRatedMovieResponse
}