package com.example.moviesapp.api

import com.example.moviesapp.model.topMovies.TopMoviesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface TopMoviesAPI {
    @Headers("X-RapidAPI-Key: 6581e9bde0msh630f926593a356cp15d017jsn908c33a298c7",
    "X-RapidAPI-Host: imdb-top-100-movies.p.rapidapi.com",
    "Content-type: application/json")
    @GET(".")
    suspend fun getTop100Movies() : Response<List<TopMoviesData>>
}