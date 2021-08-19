package com.fakhry.loonly.watchlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakhry.loonly.core.data.Resource
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class WatchlistMovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: WatchlistMovieViewModel

    @Before
    fun setUp() {
        viewModel = WatchlistMovieViewModel(FakeLoonlyInteractor())
    }

    @Test
    fun `get movie watchlist success, return true`() {
        val movies = viewModel.getMovieWatchlist.getOrAwaitValueTest()
        Truth.assertThat(movies is Resource.Success).isTrue()
    }
}