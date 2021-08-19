package com.fakhry.loonly.ui.movies.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val loonlyUseCase: LoonlyUseCase) :
    ViewModel() {
    private val _insertWatchlistMovieStatus = MutableLiveData<Resource<Movie>>()
    val insertWatchlistMovieStatus: LiveData<Resource<Movie>> = _insertWatchlistMovieStatus


    private val _delWatchlistMovieStatus = MutableLiveData<Resource<Movie>>()
    val delWatchlistMovieStatus: LiveData<Resource<Movie>> = _delWatchlistMovieStatus

    fun getMovieDetail(id: Int) = loonlyUseCase.getMovieDetails(id).asLiveData()
    fun getMovieSimilar(id: Int, page: Int) = loonlyUseCase.getMovieSimilar(id, page).asLiveData()

    fun getWatchlistStatus(id: Int) = loonlyUseCase.getWatchlistStatus(id).asLiveData()
    fun watchlistLatestOrder() = loonlyUseCase.getWatchlistLargestOrder().asLiveData()

    fun insertWatchlistMovie(movie: Movie, order: Int) {
        loonlyUseCase.insertWatchlistMovie(movie, order)
        _insertWatchlistMovieStatus.postValue(Resource.Success(movie))
    }

    fun delWatchlistMovie(movie: Movie, order: Int) {
        loonlyUseCase.delWatchlistMovie(movie, order)
        _delWatchlistMovieStatus.postValue(Resource.Success(movie))
    }
}