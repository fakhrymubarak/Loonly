package com.fakhry.loonly.ui.movies.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val loonlyUseCase: LoonlyUseCase) : ViewModel() {
    fun getMovieDetail(id: Int) = loonlyUseCase.getMovieDetails(id).asLiveData()
    fun getMovieSimilar(id: Int, page: Int) = loonlyUseCase.getMovieSimilar(id, page).asLiveData()

    fun getWatchlistStatus(id: Int) = loonlyUseCase.getWatchlistStatus(id).asLiveData()
    fun insertWatchlistMovie(movie: Movie) = loonlyUseCase.insertWatchlistMovie(movie)
    fun delWatchlistMovie(movie: Movie) = loonlyUseCase.delWatchlistMovie(movie)
}