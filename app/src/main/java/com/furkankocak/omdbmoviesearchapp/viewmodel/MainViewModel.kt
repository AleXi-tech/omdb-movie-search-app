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

    /**  Configuration change sırasında veri kaybı olmaması için stateleri
    Compose içerisinde Bundle içinde tutmak yerine yerine
    doğrudan viewModel içerisinde atıyoruz */

    var listOfMovies = mutableStateListOf<Movie>()
        private set

    var movieSpecs: MovieDetail? by mutableStateOf(null)
        private set


    //Film listesini coroutine ile çektiğimiz fonksiyon
    fun getMovieList(searchValue: String) {

        //Resetting list every time to prevent add ups and display blank list on null returns
        listOfMovies.removeAll(listOfMovies)


        viewModelScope.launch {
            runCatching {
                ApiClient.service.getData(searchValue)
            }.onSuccess { movieResponse ->
                if (movieResponse.isSuccessful) {
                    movieResponse.body()?.movieList?.let {
                        val movieListResponse =
                            it.mapNotNull { if (it.poster != null) it else null }
                        listOfMovies.addAll(movieListResponse)
                    }
                    Log.e("DATA", listOfMovies.toString())
                }
            }.getOrElse {
                Log.e("DATA ERROR", it.message.toString())
            }
        }
    }

    //Film detayları çekme
    fun getMovieSpecs(searchValue: String) {

        //Reset datas
        movieSpecs = MovieDetail()

        viewModelScope.launch {
            runCatching {
                ApiClient.service.getMovieData(searchValue)
            }.onSuccess { movieDetail ->
                if (movieDetail.isSuccessful) {
                    movieSpecs = movieDetail.body()!!
                    //Log.e("DATA", movieSpecs.value.toString())
                }
            }.getOrElse {
                Log.e("DATA ERROR", it.message.toString())
            }
        }
    }
}