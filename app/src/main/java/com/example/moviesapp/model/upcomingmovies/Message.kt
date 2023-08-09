package com.example.moviesapp.model.upcomingmovies

data class Message(
    val entries: List<Entry>,
    val group: String?=null
)