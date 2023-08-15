package com.example.moviesapp.model.favorite

import java.io.Serializable

data class FavoriteData(
    var movieImg: String,
    var movieName: String,
    var movieDescription: String?,
    var movieGenre: ArrayList<String>,
    var movieRate: String
) : Serializable
