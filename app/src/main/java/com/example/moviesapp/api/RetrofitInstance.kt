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
        retrofitMovies.create(UpcomingMoviesAPI::class.java)
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

    private val retrofitCategory by lazy{
        Retrofit.Builder()
            .baseUrl("https://advanced-movie-search.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val categoryApi by lazy {
        retrofitCategory.create(CategoryAPI::class.java)
    }

    private val retrofitSearch by lazy {
        Retrofit.Builder()
            .baseUrl("https://advanced-movie-search.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val searchApi by lazy {
        retrofitSearch.create(SearchApi::class.java)
    }

}