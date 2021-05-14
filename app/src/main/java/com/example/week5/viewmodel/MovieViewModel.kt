package com.example.week5.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week5.movie.Movie
import com.example.week5.restapi.RestClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel:ViewModel() {
    var listNowPlaying =  MutableLiveData<List<Movie>?>()
    var listTopRated =  MutableLiveData<List<Movie>?>()
    fun getNowPlaying(){
        viewModelScope.launch {
            val movieResponse = RestClient.getInstance()
                    .API.listNowPlayMovies(
                            language = "en-US",
                            page = 1
                    )
            listNowPlaying.postValue(movieResponse.results!!)
        }
    }

    fun getTopRated(){
        viewModelScope.launch {
            val movieResponse = RestClient.getInstance()
                    .API.listTopRatedMovies(
                            language = "en-US",
                            page = 1
                    )
            listTopRated.postValue(movieResponse.results!!)
        }
    }
}