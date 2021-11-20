package com.fakhry.loonly.core.data.source.remote.network

import com.fakhry.loonly.core.BuildConfig
import com.fakhry.loonly.core.data.source.remote.response.movie.details.MovieDetailsResponse
import com.fakhry.loonly.core.data.source.remote.response.movie.playings.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object {
        private const val API_KEY = BuildConfig.TMDB_API_KEY
    }

    /* Get Now Playing Movies  */
    @GET("movie/now_playing?")
    suspend fun getMovPlayings(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1
    ): ListMovieResponse

    /* Get Top Rated Movies  */
    @GET("movie/top_rated?")
    suspend fun getMovTops(
        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    /* Get Populars Movies  */
    @GET("movie/popular?")
    suspend fun getMovPopulars(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    /* Get Movie Detail  */
    @GET("movie/{id}?")
    suspend fun getMovDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieDetailsResponse

    /* Get Similar Detail  */
    @GET("movie/{id}/similar?")
    suspend fun getMovieSimilar(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

    /* Search Movie */
    @GET("search/movie?")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY
    ): ListMovieResponse

}