package com.fakhry.loonly.core.domain.usecase

import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface LoonlyUseCase {

    /*MOVIE SECTION*/
    fun getMoviePlayings(): Flow<Resource<List<Movie>>>
    fun getMoviesTop(): Flow<Resource<List<Movie>>>
    fun getMoviesTopByPage(page: Int = 1): Flow<Resource<List<Movie>>>
    fun getMoviesPopulars(): Flow<Resource<List<Movie>>>
    fun getMoviesPopularsByPage(page: Int = 1): Flow<Resource<List<Movie>>>
    fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>>
    fun getMovieSimilar(id: Int, page: Int): Flow<Resource<List<Movie>>>
    fun searchMovie(query: String, page: Int): Flow<Resource<List<Movie>>>

    /*WATCHLIST SECTION*/
    fun getWatchlistStatus(id: Int): Flow<Boolean>
    fun getWatchlistLargestOrder(): Flow<Int>
    fun getMovieWatchlist(): Flow<Resource<List<Movie>>>
    fun insertWatchlistMovie(movie: Movie, order: Int)
    fun delWatchlistMovie(movie: Movie, order: Int)
}