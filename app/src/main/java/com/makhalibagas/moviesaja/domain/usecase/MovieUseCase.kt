package com.makhalibagas.moviesaja.domain.usecase

import androidx.paging.PagingData
import com.makhalibagas.moviesaja.domain.model.Movie
import com.makhalibagas.moviesaja.domain.repository.IMovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repository: IMovieRepository,
) {

    operator fun invoke(
        genre: String, scope: CoroutineScope,
    ): Flow<PagingData<Movie>> =
        repository.getMovie(genre, scope)

}