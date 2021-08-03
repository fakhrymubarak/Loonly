package com.fakhry.loonly.core.data.source.remote.response.movie.details

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("backdrop_path")
	val backdropPath: String?,

	@field:SerializedName("poster_path")
	val posterPath: String?,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("genres")
	val genres: List<GenresItem>,

	@field:SerializedName("release_date")
	val releaseDate: String,

	@field:SerializedName("production_companies")
	val productionCompanies: List<ProductionCompaniesItem>,

	@field:SerializedName("vote_average")
	val voteAverage: Double,

	@field:SerializedName("vote_count")
	val voteCount: Int,

	@field:SerializedName("overview")
	val overview: String,

	@field:SerializedName("runtime")
	val runtime: Int,

	@field:SerializedName("tagline")
	val tagline: String
)