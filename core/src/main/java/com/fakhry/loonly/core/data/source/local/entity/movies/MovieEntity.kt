package com.fakhry.loonly.core.data.source.local.entity.movies

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_entities")
data class MovieEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "overview")
    val overview: String? = "",

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = "",

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = "",

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = 0.0,

    @ColumnInfo(name = "insert_date")
    val insertDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "categories")
    var categories: List<Int> = listOf()
)