package com.fakhry.loonly.search.di

import android.content.Context
import com.fakhry.loonly.di.SearchModuleDependencies
import com.fakhry.loonly.search.SearchMovieFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [SearchModuleDependencies::class])
interface SearchComponent {
    fun inject(fragment: SearchMovieFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(searchModuleDependencies: SearchModuleDependencies): Builder
        fun build(): SearchComponent
    }
}