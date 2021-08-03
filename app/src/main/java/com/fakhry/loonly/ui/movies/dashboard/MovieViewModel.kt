package com.fakhry.loonly.ui.movies.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val loonlyUseCase: LoonlyUseCase) : ViewModel() {
    val playingMovies = loonlyUseCase.getMoviePlayings().asLiveData()
    val topRatedMovies = loonlyUseCase.getMoviesTop().asLiveData()
    val popularMovies = loonlyUseCase.getMoviesPopulars().asLiveData()

    fun popularMovies(page: Int) =
        loonlyUseCase.getMoviesPopularsByPage(page).asLiveData()
}