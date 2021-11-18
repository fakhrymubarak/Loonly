package com.fakhry.loonly.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase

class SearchMovieViewModel(loonlyUseCase: LoonlyUseCase) : ViewModel() {
    val getMovieWatchlist = loonlyUseCase.getMovieWatchlist().asLiveData()
}