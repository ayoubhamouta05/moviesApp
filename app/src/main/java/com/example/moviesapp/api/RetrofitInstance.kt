package com.example.moviesapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://imdb188.p.rapidapi.com/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val moviesApi by lazy {
        retrofit.create(MoviesAPI::class.java)
    }
}