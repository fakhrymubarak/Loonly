package com.fakhry.loonly.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase

class SearchMovieViewModel(private val loonlyUseCase: LoonlyUseCase) : ViewModel() {
    fun getSearchedMovies(query:String) = loonlyUseCase.searchMovie(query, 1).asLiveData()
}