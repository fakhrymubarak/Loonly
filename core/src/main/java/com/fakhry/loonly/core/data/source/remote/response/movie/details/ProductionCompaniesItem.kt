package com.fakhry.loonly.core.data.source.remote.response.movie.details

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesItem(
	@field:SerializedName("name")
	val name: String
)