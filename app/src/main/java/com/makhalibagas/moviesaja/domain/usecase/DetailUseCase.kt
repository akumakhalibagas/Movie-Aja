package com.makhalibagas.moviesaja.domain.usecase

import com.makhalibagas.moviesaja.data.source.remote.Resource
import com.makhalibagas.moviesaja.domain.model.MovieDetail
import com.makhalibagas.moviesaja.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailUseCase @Inject constructor(
    private val repository: IMovieRepository,
) {

    operator fun invoke(movieId: String): Flow<Resource<MovieDetail>> =
        repository.getMovieDetail(movieId)

}