package com.makhalibagas.moviesaja.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.makhalibagas.moviesaja.data.source.remote.network.MovieApiService
import com.makhalibagas.moviesaja.data.source.remote.response.MovieReviewResponse
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class ReviewRequestPagingSource(
    private val apiService: MovieApiService,
    private val movieId: String,
) : PagingSource<Int, MovieReviewResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieReviewResponse> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGING
            val response = apiService.getMovieReviews(
                movieId = movieId,
                page = nextPageNumber
            )

            val data = response.results

            if (data.isEmpty()) return LoadResult.Page(listOf(), null, null)

            val currentPage = response.page
            val totalPage = response.totalPages

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = if (currentPage == totalPage) null else currentPage + 1
            )
        } catch (e: IOException) {
            Timber.e(e)
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieReviewResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGING = 1
        const val NETWORK_CONFIG_ITEM = 20
    }
}