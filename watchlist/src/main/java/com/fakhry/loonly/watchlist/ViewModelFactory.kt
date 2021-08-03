package com.fakhry.loonly.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val mLoonlyUseCase: LoonlyUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(WatchlistMovieViewModel::class.java) -> {
                WatchlistMovieViewModel(mLoonlyUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}