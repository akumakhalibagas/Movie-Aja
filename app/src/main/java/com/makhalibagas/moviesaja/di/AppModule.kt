package com.makhalibagas.moviesaja.di

import com.makhalibagas.moviesaja.data.source.remote.repository.MovieRepository
import com.makhalibagas.moviesaja.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieUseCase(repository: MovieRepository) =
        MovieUseCase(repository)

    @Provides
    @Singleton
    fun provideReviewUseCase(repository: MovieRepository) =
        ReviewUseCase(repository)

    @Provides
    @Singleton
    fun provideGenreUseCase(repository: MovieRepository) =
        GenreUseCase(repository)


    @Provides
    @Singleton
    fun provideDetailUseCase(repository: MovieRepository) =
        DetailUseCase(repository)

    @Provides
    @Singleton
    fun provideVideoUseCase(repository: MovieRepository) =
        VideoUseCase(repository)

}