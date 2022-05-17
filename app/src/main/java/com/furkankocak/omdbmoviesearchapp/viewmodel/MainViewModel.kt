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

    var movieSpecs: MutableList<MovieDetail> = mutableStateListOf()
    private set

    fun getMovieList(searchValue: String) {
        viewModelScope.launch {

            runCatching {
                ApiClient.service.getData(searchValue)
            }.onSuccess { movieResponse ->
                if (movieResponse.isSuccessful) {
                    val searchResults = movieResponse.body()?.movieList
                    searchResults?.let {
                        val movieList = searchResults.mapNotNull { if(it.poster != null) it else null }
                        listOfMovies = listOfMovies.copy(movieList = movieList)
                    }
                    Log.e("DATA", listOfMovies.toString())
                }
            }.getOrElse {
                Log.e("DATA ERROR", it.message.toString())
            }
        }
    }


    fun getMovieSpecs(searchValue: String) {
        viewModelScope.launch {

            runCatching {
                ApiClient.service.getMovieData(searchValue)
            }.onSuccess { movieDetail ->
                if (movieDetail.isSuccessful) {
                    val searchResults = listOf(movieDetail.body())
                    searchResults.let {
                        for (specs in searchResults) {
                            if (specs?.title != null && specs.poster != null) {
                                movieSpecs.add(specs)
                            }
                        }
                    }
                    Log.e("DATA", movieSpecs.toString())
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