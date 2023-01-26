package com.makhalibagas.moviesaja.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.makhalibagas.moviesaja.data.source.remote.Resource
import com.makhalibagas.moviesaja.domain.model.MovieReview
import com.makhalibagas.moviesaja.domain.model.MovieVideo
import com.makhalibagas.moviesaja.domain.usecase.DetailUseCase
import com.makhalibagas.moviesaja.domain.usecase.ReviewUseCase
import com.makhalibagas.moviesaja.domain.usecase.VideoUseCase
import com.makhalibagas.moviesaja.state.UiStateWrapper
import com.makhalibagas.moviesaja.utils.collectLifecycleFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val reviewUseCase: ReviewUseCase,
    private val videoUseCase: VideoUseCase,
    private val detailUseCase: DetailUseCase,
) : ViewModel() {

    private val _movieId = MutableStateFlow("")

    private val _listReviewState = MutableStateFlow<PagingData<MovieReview>?>(null)
    val listReviewState = _listReviewState.asStateFlow()

    private val _videoListState = MutableSharedFlow<UiStateWrapper<List<MovieVideo>>>()
    val videoListState = _videoListState.asSharedFlow()

    fun setMovieId(movieId: String) {
        _movieId.value = movieId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getMovieReview() {
        viewModelScope.launch {
            _movieId.flatMapLatest { genre ->
                reviewUseCase.invoke(genre, this)
            }.collect {
                _listReviewState.emit(it)
            }
        }
    }

    fun getMovieVideo() {
        collectLifecycleFlow(videoUseCase(_movieId.value)) { resource ->
            when (resource) {
                is Resource.Loading -> _videoListState.emit(UiStateWrapper.Loading(true))
                is Resource.Success -> {
                    _videoListState.emit(UiStateWrapper.Loading(false))
                    _videoListState.emit(UiStateWrapper.Success(resource.data))
                }
                is Resource.Error -> {
                    _videoListState.emit(UiStateWrapper.Loading(false))
                    _videoListState.emit(UiStateWrapper.Error(resource.msg))
                }
            }
        }
    }
}