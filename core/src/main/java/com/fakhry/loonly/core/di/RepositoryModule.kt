package com.fakhry.loonly.core.di

import com.fakhry.loonly.core.data.LoonlyRepository
import com.fakhry.loonly.core.domain.repository.ILoonlyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(loonlyRepository: LoonlyRepository): ILoonlyRepository
}