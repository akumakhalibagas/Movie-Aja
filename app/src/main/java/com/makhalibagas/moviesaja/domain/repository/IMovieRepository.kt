package com.makhalibagas.moviesaja.domain.repository

import androidx.paging.PagingData
import com.makhalibagas.moviesaja.data.source.remote.Resource
import com.makhalibagas.moviesaja.domain.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getMovie(genre: String, scope: CoroutineScope): Flow<PagingData<Movie>>
    fun getMovieReview(movieId: String, scope: CoroutineScope): Flow<PagingData<MovieReview>>
    fun getMovieGenre(): Flow<Resource<List<GenresItem>>>
    fun getMovieDetail(movieId: String): Flow<Resource<MovieDetail>>
    fun getMovieVideo(movieId: String): Flow<Resource<List<MovieVideo>>>
}