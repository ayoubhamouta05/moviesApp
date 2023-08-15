package com.example.moviesapp.api

import com.example.moviesapp.model.movieOfCategory.MovieOfCategoryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CategoryAPI {

    @Headers("X-RapidAPI-Key: 6581e9bde0msh630f926593a356cp15d017jsn908c33a298c7",
        "X-RapidAPI-Host: advanced-movie-search.p.rapidapi.com",
        "Content-type: application/json")

    @GET("discover/movie")
    suspend fun getMoviesWithCategory(
        @Query("with_genres") genreId : Int
    ) : Response<MovieOfCategoryData>



}