package com.example.moviesapp.repository

import com.example.moviesapp.api.RetrofitInstance

class MoviesRepository () {

    suspend fun getUpcomingMovies() = RetrofitInstance.moviesApi.getUpcomingMovies()

    suspend fun getTopMovies() = RetrofitInstance.topMoviesApi.getTop100Movies()

    suspend fun getMoviesWithCategory(categoryId : Int) = RetrofitInstance.categoryApi.getMoviesWithCategory(categoryId)

}