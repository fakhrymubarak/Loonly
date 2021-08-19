package com.fakhry.loonly.watchlist

import com.fakhry.loonly.core.data.Resource
import com.fakhry.loonly.core.domain.model.Company
import com.fakhry.loonly.core.domain.model.Genre
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.model.MovieDetails
import com.fakhry.loonly.core.domain.usecase.LoonlyUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Simulate the behavior of `Loonly Interactor` -> `LoonlyRepository`
 * */
class FakeLoonlyInteractor : LoonlyUseCase {
    private val mutableMovies = mutableListOf<Movie>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(state: Boolean) {
        shouldReturnNetworkError = state
    }

    private val movies = listOf(
        Movie(
            id = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            voteAverage = 9.0
        )
    )

    private val movieDetails = MovieDetails(
        id = 1,
        backdropPath = "/backdropPath",
        posterPath = "/posterPath",
        title = "Title 1",
        genres = listOf(Genre("Genre 1", 1)),
        releaseDate = "01-01-2021",
        productionCompanies = listOf(Company("Commpany 1")),
        voteAverage = 10.0,
        voteCount = 12912,
        overview = "Overview 1",
        runtime = 231,
        tagline = "Tagline 1"
    )

    private val isWatchlist = true
    private val watchlistLargestOrder = 0

    override fun getMoviePlayings(): Flow<Resource<List<Movie>>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<List<Movie>>("Network Error"))
            } else {
                emit(Resource.Success(movies))
            }
        }

    override fun getMoviesTop(): Flow<Resource<List<Movie>>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<List<Movie>>("Network Error"))
            } else {
                emit(Resource.Success(movies))
            }
        }

    override fun getMoviesTopByPage(page: Int): Flow<Resource<List<Movie>>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<List<Movie>>("Network Error"))
            } else {
                emit(Resource.Success(movies))
            }
        }

    override fun getMoviesPopulars(): Flow<Resource<List<Movie>>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<List<Movie>>("Network Error"))
            } else {
                emit(Resource.Success(movies))
            }
        }

    override fun getMoviesPopularsByPage(page: Int): Flow<Resource<List<Movie>>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<List<Movie>>("Network Error"))
            } else {
                emit(Resource.Success(movies))
            }
        }

    override fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<MovieDetails>("Network Error"))
            } else {
                emit(Resource.Success(movieDetails))
            }
        }

    override fun getMovieSimilar(id: Int, page: Int): Flow<Resource<List<Movie>>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<List<Movie>>("Network Error"))
            } else {
                emit(Resource.Success(movies))
            }
        }

    override fun searchMovie(query: String, page: Int): Flow<Resource<List<Movie>>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<List<Movie>>("Network Error"))
            } else {
                emit(Resource.Success(movies))
            }
        }

    override fun getMovieWatchlist(): Flow<Resource<List<Movie>>> =
        flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error<List<Movie>>("Network Error"))
            } else {
                emit(Resource.Success(movies))
            }
        }

    override fun insertWatchlistMovie(movie: Movie, order: Int) {
        mutableMovies.add(movie)
    }

    override fun delWatchlistMovie(movie: Movie, order: Int) {
        mutableMovies.remove(movie)
    }

    override fun getWatchlistStatus(id: Int): Flow<Boolean> =
        flow {
            emit(isWatchlist)
        }

    override fun getWatchlistLargestOrder(): Flow<Int> =
        flow {
            emit(watchlistLargestOrder)
        }
}