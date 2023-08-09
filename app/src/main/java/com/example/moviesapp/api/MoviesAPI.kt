package com.example.moviesapp.api

import com.example.moviesapp.model.upcomingmovies.UpcomingMoviesData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface MoviesAPI {

    @Headers("X-RapidAPI-Key: 6581e9bde0msh630f926593a356cp15d017jsn908c33a298c7",
    "X-RapidAPI-Host: imdb188.p.rapidapi.com",
    "Content-type: application/json")

    @GET("getUpcomingMovies?region=US")
    suspend fun getUpcomingMovies() : Response<UpcomingMoviesData>

}