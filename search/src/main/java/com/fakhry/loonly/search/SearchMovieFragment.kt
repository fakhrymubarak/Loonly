package com.fakhry.loonly.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fakhry.loonly.adapter.GridMovieAdapter
import com.fakhry.loonly.di.SearchModuleDependencies
import com.fakhry.loonly.search.databinding.FragmentSearchMovieBinding
import com.fakhry.loonly.search.di.DaggerSearchComponent
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class SearchMovieFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: SearchMovieViewModel by viewModels {
        factory
    }
    private lateinit var binding: FragmentSearchMovieBinding
    private val watchlistAdapter = GridMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerSearchComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context,
                    SearchModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }
}