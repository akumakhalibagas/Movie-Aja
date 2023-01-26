package com.makhalibagas.moviesaja.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.makhalibagas.moviesaja.data.source.remote.Resource
import com.makhalibagas.moviesaja.domain.model.GenresItem
import com.makhalibagas.moviesaja.domain.model.Movie
import com.makhalibagas.moviesaja.domain.usecase.GenreUseCase
import com.makhalibagas.moviesaja.domain.usecase.MovieUseCase
import com.makhalibagas.moviesaja.state.UiStateWrapper
import com.makhalibagas.moviesaja.utils.collectLifecycleFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val useCase: MovieUseCase,
    private val genreUseCase: GenreUseCase
) : ViewModel() {

    private val _genre = MutableStateFlow("")

    private val _listMovieState = MutableStateFlow<PagingData<Movie>?>(null)
    val listMovieState = _listMovieState.asStateFlow()

    private val _genreListState = MutableSharedFlow<UiStateWrapper<List<GenresItem>>>()
    val genreListState = _genreListState.asSharedFlow()

    fun setGenre(genre: String) {
        _genre.value = genre
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getMovie() {
        viewModelScope.launch {
            _genre.flatMapLatest { genre ->
                useCase.invoke(genre, this)
            }.collect {
                _listMovieState.emit(it)
            }
        }
    }

    fun getMovieGenre() {
        collectLifecycleFlow(genreUseCase()) { resource ->
            when (resource) {
                is Resource.Loading -> _genreListState.emit(UiStateWrapper.Loading(true))
                is Resource.Success -> {
                    _genreListState.emit(UiStateWrapper.Loading(false))
                    _genreListState.emit(UiStateWrapper.Success(resource.data))
                }
                is Resource.Error -> {
                    _genreListState.emit(UiStateWrapper.Loading(false))
                    _genreListState.emit(UiStateWrapper.Error(resource.msg))
                }
            }
        }
    }
}