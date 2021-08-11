package com.fakhry.loonly.core.utils

import com.fakhry.loonly.core.data.source.local.entity.movies.MovieEntity
import com.fakhry.loonly.core.data.source.local.entity.movies.MovieWatchlistEntity
import com.fakhry.loonly.core.data.source.remote.response.movie.details.MovieDetailsResponse
import com.fakhry.loonly.core.data.source.remote.response.movie.playings.MovieResponse
import com.fakhry.loonly.core.domain.model.Company
import com.fakhry.loonly.core.domain.model.Genre
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.model.MovieDetails

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movies = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.movieId,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.voteAverage,
                categories = listOf()
            )
            movies.add(movie)
        }
        return movies
    }

    /**
     * This mapper convert `ItemEntity`, which is a framework(room's model),
     * to Item (domain's model).
     *
     * @param input List of `ItemEntity` (room's model)
     * @return List of `Item` (domain's model)
     * */
    fun mapMovieEntitiesToDomains(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.voteAverage,
                insertDate = it.insertDate
            )
        }

    /**
     * This mapper convert Item (domain's model)
     * to `ItemEntity`, which is a framework(room's model).
     *
     * @param input List of `Item` (domain's model)
     * @return List of `ItemEntity` (room's model)
     * */
    fun mapMovieDomainToEntity(input: Movie) =
        MovieWatchlistEntity(
            id = input.id,
            title = input.title,
            overview = input.overview,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            voteAverage = input.voteAverage
        )

    fun mapMovieResponsesToDomains(input: List<MovieResponse>): List<Movie> =
        input.map {
            Movie(
                id = it.movieId,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.voteAverage,
                insertDate = System.currentTimeMillis()
            )
        }


    fun mapMovieDetailResponseToDomain(input: MovieDetailsResponse): MovieDetails {
        val listGenre = input.genres.map {
            Genre(
                name = it.name,
                id = it.id
            )
        }

        val listCompany: List<Company> =
            if (input.productionCompanies.isNotEmpty()) {
                input.productionCompanies.map {
                    Company(
                        name = it.name
                    )
                }
            } else {
                listOf(Company("Unknown Company"))
            }

        return MovieDetails(
            id = input.id,
            backdropPath = input.backdropPath,
            posterPath = input.posterPath,
            title = input.title,
            genres = listGenre,
            releaseDate = input.releaseDate,
            productionCompanies = listCompany,
            voteAverage = input.voteAverage,
            voteCount = input.voteCount,
            overview = input.overview,
            runtime = input.runtime,
            tagline = input.tagline
        )
    }

    fun mapWatchlistEntitiesToDomain(input: List<MovieWatchlistEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                voteAverage = it.voteAverage
            )
        }

}