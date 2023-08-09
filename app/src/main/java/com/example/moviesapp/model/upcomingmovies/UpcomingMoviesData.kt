package com.example.moviesapp.model.upcomingmovies

data class UpcomingMoviesData(
    val message: List<Message>,
    val status: Boolean,
    val timestamp: Long
)