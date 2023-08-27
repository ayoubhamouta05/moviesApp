package com.example.moviesapp.model.search

data class SearchData(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)