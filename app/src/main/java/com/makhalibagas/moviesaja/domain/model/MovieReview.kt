package com.makhalibagas.moviesaja.domain.model

data class MovieReview(
    val authorDetails: AuthorDetails,
    val updatedAt: String,
    val author: String,
    val createdAt: String,
    val id: String,
    val content: String,
    val url: String
)

data class AuthorDetails(
    val avatarPath: String?,
    val name: String?,
    val rating: Int?,
    val username: String?
)
