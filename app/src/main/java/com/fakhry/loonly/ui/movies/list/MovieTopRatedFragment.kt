package com.fakhry.loonly.ui.movies.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fakhry.loonly.R
import com.fakhry.loonly.adapter.GridMovieAdapter
import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.utils.viewBinding
import com.fakhry.loonly.databinding.FragmentTopRatedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieTopRatedFragment : Fragment(R.layout.fragment_top_rated) {

    private val viewModel: MovieTopRatedViewModel by viewModels()
    private val binding: FragmentTopRatedBinding by viewBinding(FragmentTopRatedBinding::bind)

    private lateinit var popularsAdapter: GridMovieAdapter
    private var currentPage: Int = 1
    private var isFetching = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularsAdapter = GridMovieAdapter()
        popularsAdapter.onItemClick = { movieId ->
            val action =
                MovieTopRatedFragmentDirections.actionNavTopRatedToNavDetails(movieId)
            findNavController().navigate(action)
        }
        binding.swipe.setOnRefreshListener {
            currentPage = 1
            loadPage(currentPage)
        }
        loadPage(currentPage)
    }

    private fun loadPage(currentPage: Int) {
        viewModel.getTopRatedMovie(currentPage).observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> {
                        if (isFetching) setLoadMoreLoading(true) else setLoading(true)
                    }
                    is Resource.Success -> {
                        movies.data?.let {
                            setAdapter(it)
                        }
                        setLoadMoreLoading(false)
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setLoadMoreLoading(false)
                        setLoading(false)
                        Toast.makeText(requireContext(), movies.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun setAdapter(data: List<Movie>) {
        if (currentPage == 1) {
            popularsAdapter.setData(data)
        } else {
            val insertIndex = popularsAdapter.listData.size
            popularsAdapter.listData.addAll(insertIndex, data)
            popularsAdapter.notifyItemRangeInserted(insertIndex, data.size)
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPopulars.layoutManager = gridLayoutManager
        binding.rvPopulars.adapter = popularsAdapter

        binding.nsv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            val dy = scrollY - oldScrollY
            if (dy > 0) {
                val itemHeight = gridLayoutManager.getChildAt(0)?.measuredHeight ?: 0
                val rvHeight = binding.rvPopulars.measuredHeight / 2
                if (scrollY > (rvHeight - itemHeight) && !isFetching) {
                    currentPage++
                    if (currentPage <= 20) loadPage(currentPage)
                    else {
                        Toast.makeText(
                            requireContext(),
                            "You've reached end of the list.",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
        }
    }

    private fun setLoading(state: Boolean) {
        isFetching = state
        binding.swipe.isRefreshing = state
    }

    private fun setLoadMoreLoading(state: Boolean) {
        isFetching = state
        binding.apply {
            if (state) {
                tvLoading.visibility = View.VISIBLE
                pbLoadingList.visibility = View.VISIBLE
            } else {
                tvLoading.visibility = View.INVISIBLE
                pbLoadingList.visibility = View.INVISIBLE
            }
        }
    }
}