package com.makhalibagas.core.domain.usecase

import androidx.paging.PagingData
import com.makhalibagas.core.domain.model.MovieReview
import com.makhalibagas.core.domain.repository.IMovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewUseCase @Inject constructor(
    private val repository: IMovieRepository,
) {

    operator fun invoke(
        genre: String, scope: CoroutineScope,
    ): Flow<PagingData<MovieReview>> =
        repository.getMovieReview(genre, scope)

}