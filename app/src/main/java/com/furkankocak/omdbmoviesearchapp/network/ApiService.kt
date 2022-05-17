package com.furkankocak.omdbmoviesearchapp.network

import com.furkankocak.omdbmoviesearchapp.data.MovieDetail
import com.furkankocak.omdbmoviesearchapp.data.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "57a7cf5c"

interface ApiService {
    @GET("/")
    suspend fun getData(@Query("s") search: String,
                        @Query("apikey") ombd_api_key: String = API_KEY): Response<MovieResponse>

    @GET("/")
    suspend fun getMovieData(@Query("i") id: String,
                             @Query("apikey") ombd_api_key: String = API_KEY): Response<MovieDetail>
}