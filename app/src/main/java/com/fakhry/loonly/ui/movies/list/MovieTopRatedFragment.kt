package com.fakhry.loonly.ui.movies.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fakhry.loonly.adapter.GridMovieAdapter
import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.databinding.FragmentTopRatedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieTopRatedFragment : Fragment() {

    private val viewModel: MovieTopRatedViewModel by viewModels()
    private lateinit var popularsAdapter: GridMovieAdapter
    private var _binding: FragmentTopRatedBinding? = null
    private var currentPage: Int = 1
    private var isFetching = false

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopRatedBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularsAdapter = GridMovieAdapter()
        popularsAdapter.onItemClick = { selectedData ->
            val action =
                MovieTopRatedFragmentDirections.actionNavTopRatedToNavDetails(selectedData.id)
            findNavController().navigate(action)
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
            popularsAdapter.addData(data)
        }

        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.rvPopulars.layoutManager = gridLayoutManager
        binding.rvPopulars.adapter = popularsAdapter

        binding.nsv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            val dy = scrollY - oldScrollY
            if (dy > 0) {
                val itemHeight = (gridLayoutManager.getChildAt(0)?.measuredHeight ?: 0) * 2
                val rvHeight = binding.rvPopulars.measuredHeight
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