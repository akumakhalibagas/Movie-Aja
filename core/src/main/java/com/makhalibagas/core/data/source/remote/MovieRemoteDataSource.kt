package com.makhalibagas.core.data.source.remote

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.makhalibagas.core.R
import com.makhalibagas.core.data.source.remote.network.ApiResponse
import com.makhalibagas.core.data.source.remote.network.MovieApiService
import com.makhalibagas.core.data.source.remote.paging.MovieRequestPagingSource
import com.makhalibagas.core.data.source.remote.paging.ReviewRequestPagingSource
import com.makhalibagas.core.data.source.remote.response.*
import com.makhalibagas.core.utils.isNetworkAvailable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRemoteDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: MovieApiService,
) {

    fun getMovie(
        genre: String,
    ): Flow<Pager<Int, MovieResponse>> = flow {
        emit(Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = MovieRequestPagingSource.NETWORK_CONFIG_ITEM
            ),
            pagingSourceFactory = {
                MovieRequestPagingSource(
                    apiService = apiService,
                    genre = genre,
                )
            }
        ))
    }.flowOn(Dispatchers.IO)

    fun getMovieReview(
        movieId: String,
    ): Flow<Pager<Int, MovieReviewResponse>> = flow {
        emit(Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = ReviewRequestPagingSource.NETWORK_CONFIG_ITEM
            ),
            pagingSourceFactory = {
                ReviewRequestPagingSource(
                    apiService = apiService,
                    movieId = movieId,
                )
            }
        ))
    }.flowOn(Dispatchers.IO)

    fun getMovieGenre(): Flow<ApiResponse<List<GenresItemResponse>>> =
        flow {
            if (!context.isNetworkAvailable()) {
                emit(ApiResponse.Error(context.getString(R.string.text_error_network_not_avail)))
                return@flow
            }

            try {
                val response = apiService.getMovieGenre()
                if (response.genres.isNotEmpty()) {
                    emit(ApiResponse.Success(response.genres))
                    return@flow
                }
                emit(ApiResponse.Error("Ops there problem"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getMovieDetail(
        movieId: String,
    ): Flow<ApiResponse<MovieDetailResponse>> =
        flow {
            if (!context.isNetworkAvailable()) {
                emit(ApiResponse.Error(context.getString(R.string.text_error_network_not_avail)))
                return@flow
            }

            try {
                val response = apiService.getMovieDetail(movieId = movieId)
                if (response != null) {
                    emit(ApiResponse.Success(response))
                    return@flow
                }
                emit(ApiResponse.Error("Ops there problem"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)

    fun getMovieVideos(
        movieId: String,
    ): Flow<ApiResponse<List<MovieVideoResponse>>> =
        flow {
            if (!context.isNetworkAvailable()) {
                emit(ApiResponse.Error(context.getString(R.string.text_error_network_not_avail)))
                return@flow
            }

            try {
                val response = apiService.getMovieVideos(movieId = movieId)
                if (response.results.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                    return@flow
                }
                emit(ApiResponse.Error("Ops there problem"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
}