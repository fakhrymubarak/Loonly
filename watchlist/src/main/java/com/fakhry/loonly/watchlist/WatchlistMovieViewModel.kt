package com.fakhry.loonly.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase

class WatchlistMovieViewModel(loonlyUseCase: LoonlyUseCase) : ViewModel() {
    val getMovieWatchlist = loonlyUseCase.getMovieWatchlist().asLiveData()
}