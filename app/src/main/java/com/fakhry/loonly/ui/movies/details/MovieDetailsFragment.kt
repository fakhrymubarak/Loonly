package com.fakhry.loonly.ui.movies.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.fakhry.loonly.DashboardActivity
import com.fakhry.loonly.R
import com.fakhry.loonly.adapter.GridMovieAdapter
import com.fakhry.loonly.core.BuildConfig
import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.model.MovieDetails
import com.fakhry.loonly.databinding.FragmentMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.floor
import kotlin.properties.Delegates

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()
    private var _binding: FragmentMovieDetailsBinding? = null

    private lateinit var similarMovieAdapter: GridMovieAdapter
    private var similarMovieCurrentPage: Int = 1
    private var isFetching = false
    private lateinit var movieDetails: MovieDetails
    private var isWatchlist by Delegates.notNull<Boolean>()


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: MovieDetailsFragmentArgs by navArgs()
        val movieId = args.movieId

        getMovieDetails(movieId)
        movieDetailsViewModel.getWatchlistStatus(movieId).observe(viewLifecycleOwner, {
            isWatchlist = it
            setWatchlistButton(isWatchlist)
        })


        binding.apply {
            swipe.setOnRefreshListener {
                getMovieDetails(movieId)
            }
        }
    }

    private fun getMovieDetails(movieId: Int) {
        movieDetailsViewModel.getMovieDetail(movieId).observe(viewLifecycleOwner, { detail ->
            if (detail != null) {
                when (detail) {
                    is Resource.Loading -> setLoading(true)

                    is Resource.Success -> {
                        detail.data?.let {
                            movieDetails = it
                            populateDetails(it)
                        }
                        setLoading(false)
                    }
                    is Resource.Error -> {
                        setLoading(false)
                        Toast.makeText(requireContext(), detail.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun populateDetails(movie: MovieDetails) {
        (activity as DashboardActivity).supportActionBar?.title = movie.title
        with(binding) {
            Glide.with(this@MovieDetailsFragment)
                .load(BuildConfig.BASE_IMAGE + movie.backdropPath)
                .into(ivBackdrop)
            Glide.with(this@MovieDetailsFragment)
                .load(BuildConfig.BASE_IMAGE + movie.posterPath)
                .into(ivPosterDetails)

            tvTitleDetails.text = movie.title
            tvGenre.text = movie.genres.joinToString(separator = " • ", transform = { it.name })
            rbRatingDetails.rating = movie.voteAverage.toFloat() / 2
            tvRatingDetails.text = getString(
                R.string.vote,
                movie.voteAverage.toString(),
                "%,d".format(movie.voteCount)
            )
            tvYearsDuration.text = getString(
                R.string.year_duration,
                movie.releaseDate.take(4),
                floor(movie.runtime / 60.0).toInt(),
                movie.runtime % 60
            )

            tvProductionCompany.text = getString(
                R.string.company,
                movie.productionCompanies.joinToString(separator = " • ", transform = { it.name }),
            )

            movie.productionCompanies.first().name
            tvTagline.text = movie.tagline
            tvSynopsisDetails.text = movie.overview
            getMovieSimilar(movie.id, false)

            btnAddWatchlist.setOnClickListener {
                isWatchlist = !isWatchlist
                if (isWatchlist) {
                    Toast.makeText(requireContext(), "Added to watchlist.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Removed from watchlist.", Toast.LENGTH_SHORT)
                        .show()
                }
                addMovieToWatchList(isWatchlist)
                setWatchlistButton(isWatchlist)
            }
            btnShare.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Movie Title\t: ${movie.title}" +
                                "\nRelease Date\t: ${movie.releaseDate} " +
                                "\nMovie Genres\t: ${tvGenre.text} " +
                                "\nMovie Rating\t: ${tvRatingDetails.text} " +
                                "\nMovie Overview\t:\n" +
                                movie.overview
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, movie.title)
                startActivity(shareIntent)
            }
        }
    }

    private fun getMovieSimilar(movieId: Int, isLoadMoreData: Boolean) {
        movieDetailsViewModel.getMovieSimilar(movieId, similarMovieCurrentPage)
            .observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> {
                            if (isLoadMoreData) setLoadMoreLoading(true) else setLoading(true)
                        }
                        is Resource.Success -> {
                            movies.data?.let {
                                setSimilarAdapter(movieId, it)
                            }
                            setLoadMoreLoading(false)
                            setLoading(false)
                        }
                        is Resource.Error -> {
                            setLoadMoreLoading(false)
                            setLoading(false)
                            Toast.makeText(requireContext(), movies.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            })
    }

    private fun setSimilarAdapter(movieId: Int, data: List<Movie>) {
        if (similarMovieCurrentPage == 1) {
            similarMovieAdapter = GridMovieAdapter()
            similarMovieAdapter.setData(data)
        } else {
            val insertIndex = similarMovieAdapter.listData.size
            similarMovieAdapter.listData.addAll(insertIndex, data)
            similarMovieAdapter.notifyItemRangeInserted(insertIndex, data.size)
        }

        similarMovieAdapter.onItemClick = { id ->
            val action = MovieDetailsFragmentDirections.actionNavDetailsSelf(id)
            findNavController().navigate(action)
        }
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvSimilar.layoutManager = gridLayoutManager
        binding.rvSimilar.adapter = similarMovieAdapter
        binding.nsv.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            val dy = scrollY - oldScrollY
            if (dy > 0) {
                val itemHeight = (gridLayoutManager.getChildAt(0)?.measuredHeight ?: 0) * 2
                val rvHeight = binding.rvSimilar.measuredHeight
                if (scrollY > (rvHeight - itemHeight) && !isFetching) {
                    similarMovieCurrentPage++
                    if (similarMovieCurrentPage <= 5) getMovieSimilar(
                        movieId,
                        isLoadMoreData = true
                    ) else {
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

    private fun addMovieToWatchList(state: Boolean) {
        val movie = Movie(
            id = movieDetails.id,
            title = movieDetails.title,
            overview = movieDetails.overview,
            posterPath = movieDetails.posterPath,
            backdropPath = movieDetails.backdropPath,
            voteAverage = movieDetails.voteAverage
        )
        if (state) {
            movieDetailsViewModel.insertWatchlistMovie(movie)
        } else {
            movieDetailsViewModel.delWatchlistMovie(movie)
        }
    }

    private fun setWatchlistButton(state: Boolean) =
        if (state) {
            binding.btnAddWatchlist.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_mark,
                0,
                0,
                0
            )
        } else {
            binding.btnAddWatchlist.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_add,
                0,
                0,
                0
            )

        }

    private fun setLoading(state: Boolean) {
        isFetching = state
        binding.swipe.isRefreshing = state
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