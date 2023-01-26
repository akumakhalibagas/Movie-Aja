package com.makhalibagas.moviesaja.data.source.remote.repository

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.makhalibagas.moviesaja.data.source.remote.MovieRemoteDataSource
import com.makhalibagas.moviesaja.data.source.remote.Resource
import com.makhalibagas.moviesaja.data.source.remote.network.ApiResponse
import com.makhalibagas.moviesaja.domain.model.*
import com.makhalibagas.moviesaja.domain.repository.IMovieRepository
import com.makhalibagas.moviesaja.utils.DataMapper.toGenresItem
import com.makhalibagas.moviesaja.utils.DataMapper.toMovie
import com.makhalibagas.moviesaja.utils.DataMapper.toMovieDetail
import com.makhalibagas.moviesaja.utils.DataMapper.toMovieReview
import com.makhalibagas.moviesaja.utils.DataMapper.toMovieVideo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(private val remoteDataSource: MovieRemoteDataSource) :
    IMovieRepository {

    override fun getMovie(genre: String, scope: CoroutineScope): Flow<PagingData<Movie>> = flow {
        val apiResource = remoteDataSource.getMovie(
            genre = genre
        ).first()

        val resourceDomain = apiResource.flow.map { pagingData ->
            pagingData.map { it.toMovie() }
        }.cachedIn(scope)

        emit(resourceDomain.first())
    }

    override fun getMovieReview(
        movieId: String, scope: CoroutineScope
    ): Flow<PagingData<MovieReview>> = flow {
        val apiResource = remoteDataSource.getMovieReview(
            movieId = movieId
        ).first()

        val resourceDomain = apiResource.flow.map { pagingData ->
            pagingData.map { it.toMovieReview() }
        }.cachedIn(scope)

        emit(resourceDomain.first())
    }

    override fun getMovieGenre(): Flow<Resource<List<GenresItem>>> = flow {
        emit(Resource.Loading)
        when (val apiResource = remoteDataSource.getMovieGenre().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResource.data.map { it.toGenresItem() }))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResource.msg))
            }
        }
    }

    override fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading)
        when (val apiResource = remoteDataSource.getMovieDetail(movieId).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResource.data.toMovieDetail()))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResource.msg))
            }
        }
    }

    override fun getMovieVideo(movieId: String): Flow<Resource<List<MovieVideo>>> = flow {
        emit(Resource.Loading)
        when (val apiResource = remoteDataSource.getMovieVideos(movieId).first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(apiResource.data.map { it.toMovieVideo() }))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResource.msg))
            }
        }
    }

}