package com.fakhry.loonly.watchlist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fakhry.loonly.adapter.GridMovieAdapter
import com.fakhry.loonly.di.WatchlistModuleDependencies
import com.fakhry.loonly.watchlist.databinding.FragmentWatchlistMovieBinding
import com.fakhry.loonly.watchlist.di.DaggerWatchlistComponent
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class WatchlistMovieFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: WatchlistMovieViewModel by viewModels {
        factory
    }
    private lateinit var binding: FragmentWatchlistMovieBinding
    private val watchlistAdapter = GridMovieAdapter()

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
        watchlistAdapter.onItemClick = { movieId ->
            val action =
                WatchlistMovieFragmentDirections.actionNavWatchlistMovieToNavDetails(movieId)
            findNavController().navigate(action)
        }
        setViewModel()
        binding.swipe.setOnRefreshListener {
            setViewModel()
        }
    }


    private fun setViewModel() {
        binding.swipe.isRefreshing = true
        viewModel.getMovieWatchlist.observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is com.fakhry.loonly.core.data.Resource.Loading -> setLoading(true)
                    is com.fakhry.loonly.core.data.Resource.Success -> {
                        setLoading(false)
                        movies.data?.let {
                            if (it.isNotEmpty()) {
                                it.forEach {
                                    Log.d("debugLoonly", it.title)
                                }
                                watchlistAdapter.setData(it)
                                binding.rvWatchlist.apply {
                                    visibility = View.VISIBLE
                                    layoutManager = GridLayoutManager(requireContext(), 2)
                                    adapter = watchlistAdapter
                                }
                                binding.apply {
                                    tvEmpty.visibility = View.GONE
                                    imgEmpty.visibility = View.GONE
                                }
                            } else {
                                binding.apply {
                                    rvWatchlist.visibility = View.GONE
                                    tvEmpty.visibility = View.VISIBLE
                                    imgEmpty.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                    is com.fakhry.loonly.core.data.Resource.Error -> {
                        setLoading(false)
                        Toast.makeText(requireContext(), movies.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

    private fun setLoading(state: Boolean) {
        binding.swipe.isRefreshing = state
    }
}