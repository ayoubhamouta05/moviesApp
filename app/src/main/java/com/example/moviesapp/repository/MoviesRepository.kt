package com.example.moviesapp.repository

import com.example.moviesapp.api.RetrofitInstance
import retrofit2.Retrofit

class MoviesRepository () {

    suspend fun getUpcomingMovies() = RetrofitInstance.moviesApi.getUpcomingMovies()

    suspend fun getTopMovies() = RetrofitInstance.topMoviesApi.getTop100Movies()

}