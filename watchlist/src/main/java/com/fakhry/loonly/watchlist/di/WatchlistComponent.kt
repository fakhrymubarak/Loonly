package com.fakhry.loonly.watchlist.di

import android.content.Context
import com.fakhry.loonly.di.WatchlistModuleDependencies
import com.fakhry.loonly.watchlist.WatchlistMovieFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [WatchlistModuleDependencies::class])
interface WatchlistComponent {
    fun inject(fragment: WatchlistMovieFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(watchlistModuleDependencies: WatchlistModuleDependencies): Builder
        fun build(): WatchlistComponent
    }
}