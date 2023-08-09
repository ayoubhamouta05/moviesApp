package com.example.moviesapp.model.upcomingmovies

data class Entry(
    val genres: List<String>,
    val id: String,
    val imageModel: ImageModel,
    val principalCredits: List<PrincipalCredit>,
    val releaseDate: String,
    val releaseYear: ReleaseYear,
    val titleText: String,
    val titleType: TitleType
)