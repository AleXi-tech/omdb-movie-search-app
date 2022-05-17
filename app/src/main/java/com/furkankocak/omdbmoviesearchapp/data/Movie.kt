package com.furkankocak.omdbmoviesearchapp.data

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search") val movieList: List<Movie>
)

data class Movie(
    @SerializedName("Title")
    var title: String,
    @SerializedName("Year")
    var year: String?,
    @SerializedName("imdbID")
    var imdbID: String?,
    @SerializedName("Poster")
    var poster: String?,
    @SerializedName("Type")
    var type: String?,
)