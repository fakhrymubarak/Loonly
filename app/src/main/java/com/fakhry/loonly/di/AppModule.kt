package com.fakhry.loonly.di

import com.fakhry.loonly.core.domain.usecase.LoonlyInteractor
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideLoonlyUseCase(loonlyInteractor: LoonlyInteractor): LoonlyUseCase
}