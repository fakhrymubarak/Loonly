package com.fakhry.loonly.ui.movies.dashboard

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakhry.loonly.FakeLoonlyInteractor
import com.fakhry.loonly.MainCoroutineRule
import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var movieViewModel: MovieViewModel

    @Before
    fun setUp() {
        movieViewModel = MovieViewModel(FakeLoonlyInteractor())
    }

    @Test
    fun `get movie playings success, returns true`() {
        val movies = movieViewModel.playingMovies.getOrAwaitValueTest()
        assertThat(movies is Resource.Success).isTrue()
    }

    @Test
    fun `get movie top rated success, returns true`() {
        val movies = movieViewModel.topRatedMovies.getOrAwaitValueTest()
        assertThat(movies is Resource.Success).isTrue()
    }

    @Test
    fun `get movie populars success, returns true`() {
        val movies = movieViewModel.popularMovies.getOrAwaitValueTest()
        assertThat(movies is Resource.Success).isTrue()
    }

    @Test
    fun `get movie populars with page success, returns true`() {
        val movies = movieViewModel.popularMovies(1).getOrAwaitValueTest()
        assertThat(movies is Resource.Success).isTrue()
    }
}