package com.fakhry.loonly.core.data.source.local

import com.fakhry.loonly.core.data.source.local.entity.movies.MovieEntity
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieWatchlistEntity
import com.fakhry.loonly.core.data.source.local.room.LoonlyDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val mLoonlyDao: LoonlyDao) {

    /* MOVIES */
    fun getMoviePlayings(): Flow<List<MovieEntity>> = mLoonlyDao.getMoviePlayings()
    fun getMovieTops(): Flow<List<MovieEntity>> = mLoonlyDao.getMovieTops()
    fun getMoviePopulars(): Flow<List<MovieEntity>> = mLoonlyDao.getMoviePopulars()

    suspend fun insertMoviePlayings(movies: List<MovieEntity>, category: Int) {
        val listMovies = mutableListOf<MovieEntity>()
        movies.forEach { movie ->
            if (mLoonlyDao.isAdded(movie.id)) {
                val oldMovie = mLoonlyDao.getMovieById(movie.id)
                movie.categories = oldMovie.categories.addCategories(category)
                mLoonlyDao.updateMovie(movie)
            } else {
                movie.categories = listOf(category)
                listMovies.add(movie)
            }
        }
        mLoonlyDao.insertMovies(listMovies)
    }

    /* TV SHOWS */

    /* WATCHLIST */
    fun getMovieWatchlist(): Flow<List<MovieWatchlistEntity>> =
        mLoonlyDao.getMovieWatchlist()

    fun getWatchlistStatus(id: Int): Flow<Boolean> =
        mLoonlyDao.getWatchlistStatus(id)

    fun insertMovieWatchlist(movieWatchlistEntity: MovieWatchlistEntity) {
        mLoonlyDao.insertMovieWatchlist(movieWatchlistEntity)
    }

    fun delMovieWatchlist(movieWatchlistEntity: MovieWatchlistEntity) {
        mLoonlyDao.delMovieWatchlist(movieWatchlistEntity)
    }

    private fun List<Int>.addCategories(newValue: Int): List<Int> {
        val tpCategory = mutableListOf<Int>()
        tpCategory.run {
            addAll(this@addCategories)
            add(newValue)
        }
        return tpCategory.distinct()
    }
}