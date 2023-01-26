package com.makhalibagas.moviesaja.utils

import com.makhalibagas.moviesaja.data.source.remote.response.*
import com.makhalibagas.moviesaja.domain.model.*

object DataMapper {

    fun MovieResponse.toMovie() = Movie(
        overview,
        originalLanguage,
        originalTitle,
        video,
        title,
        genreIds,
        base_image_url + posterPath,
        base_image_url_backdrop + backdropPath,
        releaseDate,
        popularity,
        voteAverage,
        id,
        adult,
        voteCount
    )

    fun MovieReviewResponse.toMovieReview() = MovieReview(
        authorDetails.toAuthor(), updatedAt, author, createdAt, id, content, url
    )

    private fun AuthorDetailsResponse.toAuthor() = AuthorDetails(
        avatarPath, name, rating, username
    )

    fun MovieDetailResponse.toMovieDetail() = MovieDetail(
        overview, originalTitle, posterPath, voteAverage
    )

    fun GenresItemResponse.toGenresItem() = GenresItem(name, id)

    fun MovieVideoResponse.toMovieVideo() =
        MovieVideo(site, size, iso31661, name, official, id, publishedAt, type, iso6391, key)
}