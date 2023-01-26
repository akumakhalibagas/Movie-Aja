package com.makhalibagas.core.data.source.remote.network

import com.makhalibagas.core.BuildConfig
import com.makhalibagas.core.data.source.remote.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("discover/movie")
    suspend fun getMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("with_genres") genre: String,
        @Query("page") page: Int,
    ): ResWrapper<List<MovieResponse>>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): ResWrapper<List<MovieVideoResponse>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): MovieDetailResponse

    @GET("genre/movie/list")
    suspend fun getMovieGenre(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieGenreResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int,
    ): ResWrapper<List<MovieReviewResponse>>

}