package com.example.moviesapp.model

data class NewMoviesData(
    var movieImg: String,
    var movieName: String,
    var movieGenre: List<String>,
    var movieRate: String?
)
