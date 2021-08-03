package com.fakhry.loonly.core.data.source.remote

import android.util.Log
import com.fakhry.loonly.core.data.source.remote.network.ApiResponse
import com.fakhry.loonly.core.data.source.remote.network.ApiService
import com.fakhry.loonly.core.data.source.remote.response.movie.details.MovieDetailsResponse
import com.fakhry.loonly.core.data.source.remote.response.movie.playings.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getMovPlayings(): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getMovPlayings()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getMovTops(page: Int = 1): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getMovTops(page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getMovPopulars(page: Int = 1): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getMovPopulars(page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun getMovieDetails(id: Int): Flow<ApiResponse<MovieDetailsResponse>> =
        flow {
            try {
                val response = apiService.getMovDetails(id)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)


    fun getMovieSimilar(id: Int, page: Int): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.getMovieSimilar(id, page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

    fun searchMovie(query: String, page: Int): Flow<ApiResponse<List<MovieResponse>>> =
        flow {
            try {
                val response = apiService.searchMovie(query, page)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)

}

