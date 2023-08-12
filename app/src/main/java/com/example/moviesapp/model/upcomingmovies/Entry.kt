package com.example.moviesapp.model.upcomingmovies

import java.io.Serializable

data class Entry(
    val genres: List<String>,
    val imageModel: ImageModel,
    val titleText: String,
): Serializable