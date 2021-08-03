package com.fakhry.loonly.di

import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WatchlistModuleDependencies {

    fun loonlyUseCase(): LoonlyUseCase
}