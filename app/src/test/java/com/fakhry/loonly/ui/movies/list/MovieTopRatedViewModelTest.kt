package com.fakhry.loonly.ui.movies.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fakhry.loonly.FakeLoonlyInteractor
import com.fakhry.loonly.MainCoroutineRule
import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.getOrAwaitValueTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieTopRatedViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieTopRatedViewModel

    @Before
    fun setUp() {
        viewModel = MovieTopRatedViewModel(FakeLoonlyInteractor())
    }

    @Test
    fun `get movie top rated based page success, return true`() {
        val movies = viewModel.getTopRatedMovie(1).getOrAwaitValueTest()
        Truth.assertThat(movies is Resource.Success).isTrue()
    }
}