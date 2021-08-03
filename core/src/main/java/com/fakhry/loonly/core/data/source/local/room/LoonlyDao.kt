package com.fakhry.loonly.core.data.source.local.room

import androidx.room.*
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieEntity
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieWatchlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoonlyDao {

    /* MOVIE */
    @Query("SELECT EXISTS(SELECT * FROM movie_entities WHERE id = :id)")
    suspend fun isAdded(id: Int): Boolean

    @Query("SELECT * FROM movie_entities WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieEntity

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM movie_entities WHERE categories LIKE '%0%' ORDER BY insert_date DESC")
    fun getMoviePlayings(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities WHERE categories LIKE '%1%' ORDER BY insert_date DESC")
    fun getMovieTops(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities WHERE categories LIKE '%3%' ORDER BY insert_date DESC")
    fun getMoviePopulars(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)
    /* TV */

    /* WATCHLIST */
    @Query("SELECT EXISTS(SELECT * FROM movie_watchlist_entities WHERE id = :id)")
    suspend fun getWatchlistStatus(id: Int): Boolean

    @Query("SELECT * FROM movie_watchlist_entities")
    suspend fun getMovieWatchlist(): List<MovieWatchlistEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieWatchlist(movieWatchlist: MovieWatchlistEntity)

    @Delete
    fun delMovieWatchlist(movieWatchlist: MovieWatchlistEntity)
}
