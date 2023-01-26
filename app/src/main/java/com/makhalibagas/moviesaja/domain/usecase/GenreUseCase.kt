package com.makhalibagas.moviesaja.domain.usecase

import com.makhalibagas.moviesaja.data.source.remote.Resource
import com.makhalibagas.moviesaja.domain.model.GenresItem
import com.makhalibagas.moviesaja.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val repository: IMovieRepository,
) {

    operator fun invoke(): Flow<Resource<List<GenresItem>>> = repository.getMovieGenre()

}