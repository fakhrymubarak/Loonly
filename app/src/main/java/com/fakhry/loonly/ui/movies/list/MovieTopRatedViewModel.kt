package com.fakhry.loonly.ui.movies.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieTopRatedViewModel @Inject constructor(private val loonlyUseCase: LoonlyUseCase) : ViewModel() {
    fun getTopRatedMovie(page: Int) = loonlyUseCase.getMoviesTopByPage(page).asLiveData()
}