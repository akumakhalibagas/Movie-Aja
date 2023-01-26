package com.makhalibagas.core.domain.usecase

import com.makhalibagas.core.data.source.remote.Resource
import com.makhalibagas.core.domain.model.MovieVideo
import com.makhalibagas.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoUseCase @Inject constructor(
    private val repository: IMovieRepository,
) {

    operator fun invoke(movieId: String): Flow<Resource<List<MovieVideo>>> =
        repository.getMovieVideo(movieId)

}