package com.fakhry.loonly.core.utils

import com.fakhry.loonly.core.data.source.local.entity.movies.MovieEntity
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieWatchlistEntity
import com.fakhry.loonly.core.data.source.remote.response.movie.playings.MovieResponse
import com.fakhry.loonly.core.domain.model.Movie
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class DataMapperTest {

    private lateinit var movieDomain: Movie
    private lateinit var movieResponses: MovieResponse
    private lateinit var movieEntity: MovieEntity
    private lateinit var movieWatchlistEntity: MovieWatchlistEntity

    @Before
    fun setUp() {
        movieDomain = Movie(
            id = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            voteAverage = 9.0,
            insertDate = System.currentTimeMillis()
        )

        movieResponses = MovieResponse(
            movieId = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            voteAverage = 9.0
        )

        movieEntity = MovieEntity(
            id = 1,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            voteAverage = 9.0,
            insertDate = System.currentTimeMillis(),
            categories = listOf()
        )

        movieWatchlistEntity = MovieWatchlistEntity(
            id = 1,
            order = 0,
            title = "Movie Title",
            overview = "Movie Overview",
            posterPath = "/posterPath",
            backdropPath = "/backdropPath",
            voteAverage = 9.0,

            )
    }

    @Test
    fun `list movie responses to entities is true`() {
        val entities = DataMapper.mapMovieResponsesToEntities(
            listOf(
                movieResponses,
                movieResponses,
                movieResponses
            )
        )
        assertThat(entities).isNotEmpty()
        assertThat(entities).isNotEqualTo(listOf(movieResponses, movieResponses, movieResponses))
        assertThat(entities).isNotEqualTo(listOf(movieDomain, movieDomain, movieDomain))
    }

    @Test
    fun `list movie responses to domains is true`() {
        val domains = DataMapper.mapMovieResponsesToDomains(
            listOf(
                movieResponses,
                movieResponses,
                movieResponses
            )
        )
        assertThat(domains).isNotEmpty()
        assertThat(domains).isNotEqualTo(listOf(movieResponses, movieResponses, movieResponses))
        assertThat(domains).isNotEqualTo(listOf(movieEntity, movieEntity, movieEntity))
    }

    @Test
    fun `list movie entities to domains is true`() {
        val domains =
            DataMapper.mapMovieEntitiesToDomains(listOf(movieEntity, movieEntity, movieEntity))
        assertThat(domains).isNotEmpty()
        assertThat(domains).isNotEqualTo(listOf(movieEntity, movieEntity, movieEntity))
        assertThat(domains).isNotEqualTo(listOf(movieResponses, movieResponses, movieResponses))
    }

    @Test
    fun `movie domain to watchlist entity is true`() {
        val watchlistEntity = DataMapper.mapMovieDomainToEntity(movieDomain, order = 0)
        assertThat(watchlistEntity).isEqualTo(movieWatchlistEntity)
    }

    @Test
    fun `movie watchlist entity to domain is true`() {
        val entities = DataMapper.mapWatchlistEntitiesToDomain(
            listOf(
                movieWatchlistEntity,
                movieWatchlistEntity,
                movieWatchlistEntity
            )
        )
        assertThat(entities).isNotEmpty()
        assertThat(entities).isNotEqualTo(
            listOf(
                movieWatchlistEntity,
                movieWatchlistEntity,
                movieWatchlistEntity
            )
        )
        assertThat(entities).isNotEqualTo(listOf(movieEntity, movieEntity, movieEntity))
        assertThat(entities).isNotEqualTo(listOf(movieResponses, movieResponses, movieResponses))
    }
}