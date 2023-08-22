package com.example.moviesapp.model.movieOfCategory

import java.io.Serializable

data class Result(
    var backdrop_path: String="",
    var title: String="",
    var overview: String="",
    var poster_path: String="",
    var vote_average: String=""
) : Serializable
