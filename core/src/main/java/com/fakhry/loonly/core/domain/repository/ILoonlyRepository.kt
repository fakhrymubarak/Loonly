package com.fakhry.loonly.core.domain.repository

import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface ILoonlyRepository {

    /*MOVIE SECTION*/
    fun getMoviePlayings(): Flow<Resource<List<Movie>>>
    fun getMovieTops(): Flow<Resource<List<Movie>>>
    fun getMovieTopsByPage(page: Int): Flow<Resource<List<Movie>>>
    fun getMoviePopulars(): Flow<Resource<List<Movie>>>
    fun getMoviePopularsByPage(page: Int): Flow<Resource<List<Movie>>>
    fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>
    fun getMovieSimilar(id: Int, page: Int): Flow<Resource<List<Movie>>>
    fun searchMovie(query: String, page: Int): Flow<Resource<List<Movie>>>

    /*WATCHLIST SECTION*/
    fun getMovieWatchlist(): Flow<List<Movie>>
    fun getWatchlistStatus(id: Int): Flow<Boolean>
    fun insertWatchlistMovie(movie: Movie)
    fun deleteWatchlistMovie(movie: Movie)
}