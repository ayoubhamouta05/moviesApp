package com.example.moviesapp.model

data class FavoriteData(
    var movieImg: String,
    var movieName: String,
    var movieDescription: String?,
    var movieGenre: ArrayList<String>,
    var movieRate: String
)
