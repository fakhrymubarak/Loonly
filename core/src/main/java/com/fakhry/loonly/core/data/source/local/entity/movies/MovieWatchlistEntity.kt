package com.fakhry.loonly.core.data.source.local.entity.movies

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_watchlist_entities")
data class MovieWatchlistEntity(

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
    val voteAverage: Double? = 0.0
)