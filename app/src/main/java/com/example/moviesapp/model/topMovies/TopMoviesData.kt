package com.example.moviesapp.model.topMovies

import java.io.Serializable

data class TopMoviesData(
    var title: String,
    var description: String,
    var image: String,
    var genre: ArrayList<String>,
    var rating: String
) : Serializable
