package com.fakhry.loonly.core.domain.usecase

import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.model.MovieDetails
import com.fakhry.loonly.core.domain.repository.ILoonlyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoonlyInteractor @Inject constructor(private val mLoonlyRepository: ILoonlyRepository) :
    LoonlyUseCase {

    /*MOVIE SECTION*/
    override fun getMoviePlayings(): Flow<Resource<List<Movie>>> =
        mLoonlyRepository.getMoviePlayings()

    override fun getMoviesTop(): Flow<Resource<List<Movie>>> =
        mLoonlyRepository.getMovieTops()

    override fun getMoviesTopByPage(page: Int): Flow<Resource<List<Movie>>> =
        mLoonlyRepository.getMovieTopsByPage(page)

    override fun getMoviesPopulars(): Flow<Resource<List<Movie>>> =
        mLoonlyRepository.getMoviePopulars()

    override fun getMoviesPopularsByPage(page: Int): Flow<Resource<List<Movie>>> =
        mLoonlyRepository.getMoviePopularsByPage(page)

    override fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> =
        mLoonlyRepository.getMovieDetails(id)

    override fun getMovieSimilar(id: Int, page: Int): Flow<Resource<List<Movie>>> =
        mLoonlyRepository.getMovieSimilar(id, page)

    override fun searchMovie(query: String, page: Int): Flow<Resource<List<Movie>>> =
        mLoonlyRepository.searchMovie(query, page)

    /*WATCHLIST SECTION*/
    override fun getMovieWatchlist(): Flow<Resource<List<Movie>>> =
        mLoonlyRepository.getMovieWatchlist()

    override fun getWatchlistStatus(id: Int): Flow<Boolean> =
        mLoonlyRepository.getWatchlistStatus(id)

    override fun getWatchlistLargestOrder(): Flow<Int> =
        mLoonlyRepository.getWatchlistLargestOrder()

    override fun insertWatchlistMovie(movie: Movie, order: Int) =
        mLoonlyRepository.insertWatchlistMovie(movie, order)

    override fun delWatchlistMovie(movie: Movie, order: Int) =
        mLoonlyRepository.deleteWatchlistMovie(movie, order)

}