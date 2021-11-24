package com.fakhry.loonly.ui.movies.dashboard

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.fakhry.loonly.R
import com.fakhry.loonly.adapter.CarouselAdapter
import com.fakhry.loonly.adapter.GridMovieAdapter
import com.fakhry.loonly.adapter.ListPosterAdapter
import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.utils.viewBinding
import com.fakhry.loonly.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movies) {

    private val movieViewModel: MovieViewModel by viewModels()
    private val binding: FragmentMoviesBinding by viewBinding(FragmentMoviesBinding::bind)

    private lateinit var popularsAdapter: GridMovieAdapter
    private var starterCarouselItem: Int = 3
    private var popularMovieCurrentPage: Int = 1
    private var isFetching = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewModel()
        binding.apply {
            swipe.setOnRefreshListener {
                setViewModel()
            }
            btnTopRated.setOnClickListener {
                findNavController().navigate(R.id.action_nav_movies_to_nav_top_rated)
            }
        }
    }

    override fun onDestroyView() {
        binding.rvPopulars.adapter = null
        binding.rvPopulars.adapter = null
        binding.vpImageSlider.adapter = null
        super.onDestroyView()
    }

    private fun setViewModel() {
        movieViewModel.playingMovies.observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        setCarouselAdapter(movies.data)
                    }
                    is Resource.Error -> {
                        setLoading(false)
                        Toast.makeText(requireContext(), movies.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        movieViewModel.topRatedMovies.observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        setTopRatedAdapter(movies.data)
                    }
                    is Resource.Error -> {
                        setLoading(false)
                        Toast.makeText(requireContext(), movies.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
        movieViewModel.popularMovies.observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        movies.data?.let { setPopularsAdapter(it) }
                    }
                    is Resource.Error -> {
                        setLoading(false)
                        Toast.makeText(requireContext(), movies.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

    }


    private fun setCarouselAdapter(data: List<Movie>?) {
        val sliderAdapter = CarouselAdapter()
        sliderAdapter.setData(data)

        sliderAdapter.onItemClick = { selectedData ->
            val action = MovieFragmentDirections.actionNavMoviesToNavDetails(selectedData.id)
            findNavController().navigate(action)
        }

        binding.vpImageSlider.adapter = sliderAdapter
        setCarouselViewPager()
    }

    private fun setCarouselViewPager() {
        val viewPagerSlider = binding.vpImageSlider
        viewPagerSlider.apply {
            offscreenPageLimit = 3
            setCurrentItem(starterCarouselItem, false)
            setPageTransformer(CompositePageTransformer().also { cpt ->
                cpt.addTransformer(MarginPageTransformer(12))
            })
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                val padding = resources.getDimensionPixelOffset(R.dimen.halfPageMargin)
                setPadding(padding, 0, padding, 0)
                clipToPadding = false
            }
        }
    }

    private fun setTopRatedAdapter(data: List<Movie>?) {
        val topRatedAdapter = ListPosterAdapter()
        topRatedAdapter.setData(data)
        topRatedAdapter.onItemClick = { selectedData ->
            val action = MovieFragmentDirections.actionNavMoviesToNavDetails(selectedData.id)
            findNavController().navigate(action)
        }
        binding.rvTopRated.adapter = topRatedAdapter
    }

    private fun setPopularsAdapter(data: List<Movie>) {
        if (popularMovieCurrentPage == 1) {
            popularsAdapter = GridMovieAdapter()
            popularsAdapter.setData(data)
        } else {
            val insertIndex = popularsAdapter.listData.size
            popularsAdapter.listData.addAll(insertIndex, data)
            popularsAdapter.notifyItemRangeInserted(insertIndex, data.size)
        }

        popularsAdapter.onItemClick = { movieId ->
            val action = MovieFragmentDirections.actionNavMoviesToNavDetails(movieId)
            findNavController().navigate(action)
        }
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvPopulars.layoutManager = gridLayoutManager
        binding.rvPopulars.adapter = popularsAdapter

        binding.nsv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            val dy = scrollY - oldScrollY
            if (dy > 0) {
                val itemHeight = (gridLayoutManager.getChildAt(0)?.measuredHeight ?: 0) * 2
                val rvHeight = binding.rvPopulars.measuredHeight
                if (scrollY > (rvHeight - itemHeight) && !isFetching) {
                    popularMovieCurrentPage++
                    if (popularMovieCurrentPage <= 10) loadNextPopularPage()
                    else {
                        Toast.makeText(
                            requireContext(),
                            "You've reached end of the list.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun loadNextPopularPage() {
        movieViewModel.popularMovies(popularMovieCurrentPage)
            .observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> setLoadMoreLoading(true)
                        is Resource.Success -> {
                            setLoadMoreLoading(false)
                            movies.data?.let { setPopularsAdapter(it) }
                        }
                        is Resource.Error -> {
                            setLoadMoreLoading(false)
                            Toast.makeText(requireContext(), movies.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })
    }

    private fun setLoading(state: Boolean) {
        binding.swipe.isRefreshing = state
        isFetching = state
        if(state){
            binding.apply{
                pbImageSlider.visibility = View.VISIBLE
                pbTopRated.visibility = View.VISIBLE
                pbPopulars.visibility = View.VISIBLE
                vpImageSlider.visibility = View.INVISIBLE
                rvTopRated.visibility = View.INVISIBLE
                rvPopulars.visibility = View.INVISIBLE
            }
        }else{
            binding.apply{
                pbImageSlider.visibility = View.INVISIBLE
                pbTopRated.visibility = View.INVISIBLE
                pbPopulars.visibility = View.INVISIBLE
                vpImageSlider.visibility = View.VISIBLE
                rvTopRated.visibility = View.VISIBLE
                rvPopulars.visibility = View.VISIBLE
            }
        }

    }

    private fun setLoadMoreLoading(state: Boolean) {
        isFetching = state
        if (state) {
            binding.tvLoading.visibility = View.VISIBLE
            binding.pbLoadingList.visibility = View.VISIBLE
        } else {
            binding.tvLoading.visibility = View.INVISIBLE
            binding.pbLoadingList.visibility = View.INVISIBLE
        }
    }
}