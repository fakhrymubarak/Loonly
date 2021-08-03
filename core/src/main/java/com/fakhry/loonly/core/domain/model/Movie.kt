package com.fakhry.loonly.core.domain.model

data class Movie(
    val id: Int = 0,
    val title: String = "",
    val overview: String? = "",
    val posterPath: String? = "",
    val backdropPath: String? = "",
    val voteAverage: Double? = 0.0,
    val insertDate: Long = System.currentTimeMillis()
)
