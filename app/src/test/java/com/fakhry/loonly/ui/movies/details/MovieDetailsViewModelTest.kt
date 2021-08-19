package com.fakhry.loonly.ui.movies.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakhry.loonly.FakeLoonlyInteractor
import com.fakhry.loonly.MainCoroutineRule
import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var movie: Movie

    @Before
    fun setUp() {
        viewModel = MovieDetailsViewModel(FakeLoonlyInteractor())
        movie = Movie(
            id = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            voteAverage = 9.0
        )
    }

    @Test
    fun `get movie details based id success, return true`() {
        val movies = viewModel.getMovieDetail(1).getOrAwaitValueTest()
        assertThat(movies is Resource.Success).isTrue()
    }

    @Test
    fun `get movie similar based id success, return true`() {
        val movies = viewModel.getMovieSimilar(1, 1).getOrAwaitValueTest()
        assertThat(movies is Resource.Success).isTrue()
    }


    @Test
    fun `get watchlist status true, return true`() {
        val watchlistStatus = viewModel.getWatchlistStatus(1).getOrAwaitValueTest()
        assertThat(watchlistStatus).isTrue()
    }


    @Test
    fun `get watchlist latest order, return true`() {
        val latestOrder = viewModel.watchlistLatestOrder().getOrAwaitValueTest()
        assertThat(latestOrder).isEqualTo(0)
    }


    @Test
    fun `insert watchlist movies success, return true`() {
        viewModel.insertWatchlistMovie(movie, 0)
        val status = viewModel.insertWatchlistMovieStatus.getOrAwaitValueTest()
        assertThat(status is Resource.Success<*>).isTrue()
    }


    @Test
    fun `delete watchlist movies success, return true`() {
        viewModel.delWatchlistMovie(movie, 0)
        val status = viewModel.delWatchlistMovieStatus.getOrAwaitValueTest()
        assertThat(status is Resource.Success<*>).isTrue()
    }
}