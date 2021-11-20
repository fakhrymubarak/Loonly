package com.fakhry.loonly.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fakhry.loonly.adapter.GridMovieAdapter
import com.fakhry.loonly.core.utils.viewBinding
import com.fakhry.loonly.di.SearchModuleDependencies
import com.fakhry.loonly.search.databinding.FragmentSearchMovieBinding
import com.fakhry.loonly.search.di.DaggerSearchComponent
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class SearchMovieFragment : Fragment(R.layout.fragment_search_movie) {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: SearchMovieViewModel by viewModels { factory }

    private val binding: FragmentSearchMovieBinding by viewBinding(FragmentSearchMovieBinding::bind)
    private val searchMovieAdapter = GridMovieAdapter()
    private var currentQuery = ""

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchMovieAdapter.onItemClick = { movieId ->
            val action =
                SearchMovieFragmentDirections.actionNavSearchMovieToNavDetails(movieId)
            findNavController().navigate(action)
        }
        setSearchView()
        binding.swipe.setOnRefreshListener {
            setViewModel(currentQuery)
        }
    }

    private fun setSearchView() {
        binding.svSearchMovie.onActionViewExpanded()
        binding.svSearchMovie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMovieAdapter.setData(listOf())
                setViewModel(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                currentQuery = newText
                return false
            }
        })
    }

    private fun setViewModel(query: String) {
        setLoading(true)
        viewModel.getSearchedMovies(query).observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is com.fakhry.loonly.core.data.Resource.Loading -> setLoading(true)
                    is com.fakhry.loonly.core.data.Resource.Success -> {
                        setLoading(false)
                        movies.data?.let {
                            if (it.isNotEmpty()) {
                                searchMovieAdapter.setData(it)
                                binding.rvSearchMovies.apply {
                                    layoutManager = GridLayoutManager(requireContext(), 2)
                                    adapter = searchMovieAdapter
                                }
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Movie not found.",
                                    Toast.LENGTH_SHORT
                                ).show()
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