package com.fakhry.loonly.core.data.source.local.room

import androidx.room.*
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieEntity
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieWatchlistEntity
import com.fakhry.loonly.core.const.Const
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

    @Query("SELECT * FROM movie_entities WHERE categories LIKE :idCategory ORDER BY insert_date DESC")
    fun getMoviePlayings(idCategory: String = "%${Const.ID_CAT_NOW_PLAYING}%"): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities WHERE categories LIKE :idCategory ORDER BY insert_date DESC")
    fun getMovieTops(idCategory: String = "%${Const.ID_CAT_TOP_RATED}%"): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie_entities WHERE categories LIKE :idCategory ORDER BY insert_date DESC")
    fun getMoviePopulars(idCategory: String = "%${Const.ID_CAT_POPULAR}%"): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    /* WATCHLIST */
    @Query("SELECT EXISTS(SELECT * FROM movie_watchlist_entities WHERE id = :id)")
    fun getWatchlistStatus(id: Int): Flow<Boolean>

    @Query("SELECT MAX(`order`) FROM movie_watchlist_entities")
    fun getLargestOrder(): Flow<Int>

    @Query("SELECT * FROM movie_watchlist_entities ORDER BY `order`")
    fun getMovieWatchlist(): Flow<List<MovieWatchlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieWatchlist(movieWatchlist: MovieWatchlistEntity)

    @Delete
    fun delMovieWatchlist(movieWatchlist: MovieWatchlistEntity)
}
