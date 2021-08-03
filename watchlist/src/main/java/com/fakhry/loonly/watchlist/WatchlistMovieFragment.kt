package com.fakhry.loonly.watchlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fakhry.loonly.di.WatchlistModuleDependencies
import com.fakhry.loonly.watchlist.databinding.FragmentWatchlistMovieBinding
import com.fakhry.loonly.watchlist.di.DaggerWatchlistComponent
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class WatchlistMovieFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val movieViewModel: WatchlistMovieViewModel by viewModels {
        factory
    }
    private lateinit var binding: FragmentWatchlistMovieBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWatchlistMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerWatchlistComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context,
                    WatchlistModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("debugLoonly", "tested")

        movieViewModel.getMovieWatchlist.observe(viewLifecycleOwner, { movies ->
            movies.map { movie ->
                Log.d("debugLoonly", movie.title)
            }
        })
    }

}