package com.fakhry.loonly.core.domain.model


data class MovieDetails(
    val id: Int,
    val backdropPath: String?,
    val posterPath: String?,
    val title: String,
    val genres: List<Genre>,
    val releaseDate: String,
    val productionCompanies: List<Company>,
    val voteAverage: Double,
    val voteCount: Int,
    val overview: String,
    val runtime: Int,
    val tagline: String
)
