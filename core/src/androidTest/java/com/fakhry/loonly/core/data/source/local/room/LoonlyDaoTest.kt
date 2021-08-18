package com.fakhry.loonly.core.data.source.local.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieEntity
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieWatchlistEntity
import com.fakhry.loonly.core.getOrAwaitValue
import com.fakhry.loonly.core.utils.Const
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LoonlyDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: LoonlyDatabase
    private lateinit var dao: LoonlyDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LoonlyDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.loonlyDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetMoviesBasedCategories() = runBlockingTest {
        val movies = listOf(
            MovieEntity(
                id = 1,
                title = "Movie Title",
                overview = "Movie Overview",
                posterPath = "/posterPath",
                backdropPath = "/backdropPath",
                voteAverage = 10.0,
                categories = listOf(Const.ID_CAT_NOW_PLAYING, Const.ID_CAT_POPULAR)
            ),
            MovieEntity(
                id = 2,
                title = "Movie Title 2",
                overview = "Movie Overview 2",
                posterPath = "/posterPath 2",
                backdropPath = "/backdropPath 2",
                voteAverage = 9.0,
                categories = listOf(Const.ID_CAT_TOP_RATED)
            )
        )
        dao.insertMovies(movies)

        val movieById = dao.getMovieById(movies.first().id)
        val moviePlayings = dao.getMoviePlayings().asLiveData().getOrAwaitValue()
        val movieTops = dao.getMovieTops().asLiveData().getOrAwaitValue()
        val moviePopulars = dao.getMoviePopulars().asLiveData().getOrAwaitValue()

        assertThat(moviePlayings).isNotEmpty()
        assertThat(movieTops).isNotEmpty()
        assertThat(moviePopulars).isNotEmpty()

        assertThat(movieById).isEqualTo(movies.first())
        assertThat(moviePlayings).contains(movies.first())
        assertThat(movieTops).contains(movies.last())
        assertThat(moviePopulars).contains(movies.first())
    }


    @Test
    fun checkIsAddedThenUpdateMovies() = runBlockingTest {
        val movies = listOf(
            MovieEntity(
                id = 1,
                title = "Movie Title",
                overview = "Movie Overview",
                posterPath = "/posterPath",
                backdropPath = "/backdropPath",
                voteAverage = 10.0,
                categories = listOf(Const.ID_CAT_NOW_PLAYING)
            )
        )
        dao.insertMovies(movies)
        val moviePlayings = dao.getMoviePlayings().asLiveData().getOrAwaitValue()
        assertThat(moviePlayings).contains(movies.first())

        val isAdded = dao.isAdded(movies.first().id)
        assertThat(isAdded).isTrue()

        movies.first().categories = listOf(Const.ID_CAT_NOW_PLAYING, Const.ID_CAT_POPULAR)
        val updatedMovie = movies.first()
        dao.updateMovie(updatedMovie)

        val moviePopulars = dao.getMoviePopulars().asLiveData().getOrAwaitValue()
        assertThat(moviePopulars).contains(updatedMovie)
    }

    @Test
    fun insertAndCheckStatusThenGetWatchlist() = runBlockingTest {
        val movie = MovieWatchlistEntity(
            id = 1,
            order = 0,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            voteAverage = 10.0
        )

        dao.insertMovieWatchlist(movie)

        val watchlistStatus = dao.getWatchlistStatus(movie.id).asLiveData().getOrAwaitValue()
        val listWatchlistMovies = dao.getMovieWatchlist().asLiveData().getOrAwaitValue()

        assertThat(listWatchlistMovies).isNotEmpty()
        assertThat(watchlistStatus).isTrue()
        assertThat(listWatchlistMovies).contains(movie)
    }

    @Test
    fun deleteWatchlist() = runBlockingTest {
        val movie = MovieWatchlistEntity(
            id = 1,
            order = 0,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            voteAverage = 10.0
        )

        dao.insertMovieWatchlist(movie)
        dao.delMovieWatchlist(movie)
        val listWatchlistMovies = dao.getMovieWatchlist().asLiveData().getOrAwaitValue()
        assertThat(listWatchlistMovies).doesNotContain(movie)
    }
}