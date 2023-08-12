package com.example.moviesapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofitMovies by lazy {
        Retrofit.Builder()
            .baseUrl("https://imdb188.p.rapidapi.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val moviesApi by lazy {
        retrofitMovies.create(MoviesAPI::class.java)
    }

    private val retrofitTopMovies by lazy {
        Retrofit.Builder()
            .baseUrl("https://imdb-top-100-movies.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val topMoviesApi by lazy {
        retrofitTopMovies.create(TopMoviesAPI::class.java)
    }

}