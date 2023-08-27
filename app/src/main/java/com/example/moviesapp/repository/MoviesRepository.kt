package com.example.moviesapp.repository

import android.graphics.pdf.PdfDocument.Page
import com.example.moviesapp.api.RetrofitInstance

class MoviesRepository () {

    suspend fun getUpcomingMovies() = RetrofitInstance.moviesApi.getUpcomingMovies()

    suspend fun getTopMovies() = RetrofitInstance.topMoviesApi.getTop100Movies()

    suspend fun getMoviesWithCategory(categoryId : Int) = RetrofitInstance.categoryApi.getMoviesWithCategory(categoryId)

    suspend fun getSearchMovies(movieName : String , page: Int) = RetrofitInstance.searchApi.getSearchMovies(movieName,page)
}