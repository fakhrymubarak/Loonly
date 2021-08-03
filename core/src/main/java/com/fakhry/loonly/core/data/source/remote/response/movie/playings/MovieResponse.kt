package com.fakhry.loonly.core.data.source.remote.response.movie.playings

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @field:SerializedName("id")
    val movieId: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("overview")
    val overview: String? = null,

    @field:SerializedName("poster_path")
    val posterPath: String? = null,

    @field:SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @field:SerializedName("vote_average")
    val voteAverage: Double? = null
)