package com.fakhry.loonly.core.data

import com.fakhry.loonly.core.const.Const
import com.fakhry.loonly.core.data.source.local.LocalDataSource
import com.fakhry.loonly.core.data.source.remote.RemoteDataSource
import com.fakhry.loonly.core.data.source.remote.network.ApiResponse
import com.fakhry.loonly.core.data.source.remote.response.movie.playings.MovieResponse
import com.fakhry.loonly.core.domain.model.Movie
import com.fakhry.loonly.core.domain.model.MovieDetails
import com.fakhry.loonly.core.domain.repository.ILoonlyRepository
import com.fakhry.loonly.core.utils.DataMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoonlyRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ILoonlyRepository {

    /*MOVIE SECTION*/
    override fun getMoviePlayings(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getMoviePlayings().map { DataMapper.mapMovieEntitiesToDomains(it) }

            override fun shouldFetch(data: List<Movie>?): Boolean = fetchState(data)

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovPlayings()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMoviePlayings(movieList, Const.ID_CAT_NOW_PLAYING)
            }
        }.asFlow()


    override fun getMovieTops(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getMovieTops().map { DataMapper.mapMovieEntitiesToDomains(it) }

            override fun shouldFetch(data: List<Movie>?): Boolean = fetchState(data)

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovTops()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMoviePlayings(movieList, Const.ID_CAT_TOP_RATED)
            }
        }.asFlow()

    override fun getMovieTopsByPage(page: Int): Flow<Resource<List<Movie>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getMovTops(page).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapMovieResponsesToDomains(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Success(listOf()))

            }
        }

    override fun getMoviePopulars(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> =
                localDataSource.getMoviePopulars().map { DataMapper.mapMovieEntitiesToDomains(it) }

            override fun shouldFetch(data: List<Movie>?): Boolean = fetchState(data)

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getMovPopulars()

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMoviePlayings(movieList, Const.ID_CAT_POPULAR)
            }
        }.asFlow()

    override fun getMoviePopularsByPage(page: Int): Flow<Resource<List<Movie>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getMovPopulars(page).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapMovieResponsesToDomains(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Success(listOf()))
            }
        }

    override fun getMovieDetails(id: Int): Flow<Resource<MovieDetails>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getMovieDetails(id).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapMovieDetailResponseToDomain(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Error(message = "Movies not Found"))
            }
        }

    override fun getMovieSimilar(id: Int, page: Int): Flow<Resource<List<Movie>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.getMovieSimilar(id, page).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapMovieResponsesToDomains(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Success(listOf()))
            }
        }

    override fun searchMovie(query: String, page: Int): Flow<Resource<List<Movie>>> =
        flow {
            emit(Resource.Loading())
            when (val apiResponse = remoteDataSource.searchMovie(query, page).first()) {
                is ApiResponse.Success -> {
                    val data = DataMapper.mapMovieResponsesToDomains(apiResponse.data)
                    emit(Resource.Success(data))
                }
                is ApiResponse.Error -> emit(Resource.Error(apiResponse.errorMessage))
                is ApiResponse.Empty -> emit(Resource.Success(listOf()))
            }
        }


    /*WATCHLIST SECTION*/
    override fun getMovieWatchlist(): Flow<Resource<List<Movie>>> =
        flow {
            emit(Resource.Loading())
            val data = localDataSource.getMovieWatchlist().map {
                DataMapper.mapWatchlistEntitiesToDomain(it)
            }
            emitAll(data.map { Resource.Success(it) })
        }

    override fun getWatchlistStatus(id: Int): Flow<Boolean> =
        flow {
            emitAll(localDataSource.getWatchlistStatus(id))
        }


    override fun getWatchlistLargestOrder(): Flow<Int> =
        flow {
            emitAll(localDataSource.getWatchlistLargestOrder())
        }

    override fun insertWatchlistMovie(movie: Movie, order: Int) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie, order)
        CoroutineScope(Dispatchers.IO).launch { localDataSource.insertMovieWatchlist(movieEntity) }
    }

    override fun deleteWatchlistMovie(movie: Movie, order: Int) {
        val movieEntity = DataMapper.mapMovieDomainToEntity(movie, order)
        CoroutineScope(Dispatchers.IO).launch { localDataSource.delMovieWatchlist(movieEntity) }
    }


    /**
     * This function will **return** `true`if data is null
     * or data is empty or today is sunday or today is thursday
     * */
    private fun fetchState(data: List<Movie>?): Boolean = data == null ||
            data.isEmpty() ||
            Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 1 ||
            Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 4
}