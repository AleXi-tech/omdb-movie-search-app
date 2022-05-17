package com.furkankocak.omdbmoviesearchapp.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.furkankocak.omdbmoviesearchapp.data.Movie
import com.furkankocak.omdbmoviesearchapp.data.MovieDetail
import com.furkankocak.omdbmoviesearchapp.network.ApiClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var listOfMovies:MovieListState by mutableStateOf(MovieListState(listOf()))
    private set

    var movieSpecs: MovieDetailState by mutableStateOf(MovieDetailState(null))
    private set

    fun getMovieList(searchValue: String) {
        viewModelScope.launch {
            runCatching {
                ApiClient.service.getData(searchValue)
            }.onSuccess { movieResponse ->
                if (movieResponse.isSuccessful) {
                    movieResponse.body()?.movieList?.let {
                        val movieListResponse = it.mapNotNull { if(it.poster != null) it else null }
                        listOfMovies = listOfMovies.copy(movieList = movieListResponse)
                    }
                    Log.e("DATA", listOfMovies.toString())
                }
            }.getOrElse {
                Log.e("DATA ERROR", it.message.toString())
            }
        }
    }


    fun getMovieSpecs(searchValue: String) {
        movieSpecs = movieSpecs.copy(movieDetail = null)

        viewModelScope.launch {

            runCatching {
                ApiClient.service.getMovieData(searchValue)
            }.onSuccess { movieDetail ->
                if (movieDetail.isSuccessful) {
                    movieSpecs = movieSpecs.copy(movieDetail = movieDetail.body())
                }
            }.getOrElse {
                Log.e("DATA ERROR", it.message.toString())
            }
        }
    }
}

data class MovieListState(
    val movieList: List<Movie>
)

data class MovieDetailState(
    val movieDetail: MovieDetail? = null
)