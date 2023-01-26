package com.makhalibagas.core.domain.usecase

import com.makhalibagas.core.data.source.remote.Resource
import com.makhalibagas.core.domain.model.GenresItem
import com.makhalibagas.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreUseCase @Inject constructor(
    private val repository: IMovieRepository,
) {

    operator fun invoke(): Flow<Resource<List<GenresItem>>> = repository.getMovieGenre()

}